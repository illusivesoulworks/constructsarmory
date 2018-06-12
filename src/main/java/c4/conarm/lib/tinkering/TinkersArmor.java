/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.lib.tinkering;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.client.models.ModelBrokenArmor;
import c4.conarm.client.models.ModelConstructsArmor;
import c4.conarm.lib.client.DynamicTextureHelper;
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.modifiers.IArmorModifyable;
import c4.conarm.lib.traits.IArmorTrait;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class TinkersArmor extends ItemArmor implements ITinkerable, IArmorModifyable, IRepairable, ISpecialArmor {

    protected final PartMaterialType[] requiredComponents;
    protected ModelBiped model;
    protected ModelBiped brokenModel;
    private static final ItemArmor.ArmorMaterial emptyMaterial = EnumHelper.addArmorMaterial("armory", "empty", 0, new int[]{0,0,0,0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

    public TinkersArmor(EntityEquipmentSlot slotIn, PartMaterialType... requiredComponents) {
        super(emptyMaterial, 0, slotIn);
        this.requiredComponents = requiredComponents;
        this.setMaxStackSize(1);
    }

    /* Armor Traits */
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){

        if (!ToolHelper.isBroken(itemStack)) {
            NBTTagList list = TagUtil.getTraitsTagList(itemStack);
            for(int i = 0; i < list.tagCount(); i++) {
                ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                if(trait != null) {
                    trait.onArmorTick(itemStack, world, player);
                }
            }
        }
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase entity, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {

        if (!ToolHelper.isBroken(armor) && !source.isUnblockable() && entity instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) entity;
            ArmorModifications mods = new ArmorModifications(ArmorHelper.getArmor(armor, slot), ArmorHelper.getToughness(armor));
            NBTTagList list = TagUtil.getTraitsTagList(armor);

            for (int i = 0; i < list.tagCount(); i++) {
                ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                if (trait != null && trait instanceof IArmorTrait) {
                    IArmorTrait armorTrait = (IArmorTrait) trait;
                    mods = armorTrait.getModifications(player, mods, armor, source, damage, slot);
                }
            }

            float totalToughness = mods.toughness * mods.toughnessMod * mods.effective;
            float totalArmor = mods.armor * mods.armorMod * mods.effective;

            ArmorProperties prop = ArmorHelper.getPropertiesAfterAbsorb(armor, damage, totalArmor, totalToughness, armorType);

            //Subtract armor and toughness so that ISpecialArmor does not calculate it twice
            prop.Armor -= getArmorDifference(ArmorHelper.getArmor(armor, slot), entity);
            prop.Toughness -= ArmorHelper.getToughness(armor);

            return prop;
        }

        return new ArmorProperties(0, 0, 0);
    }

    //Fractional armor values don't get calculated properly so we call this method to compensate
    protected double getArmorDifference(float armor, EntityLivingBase entity) {
        double currentArmor = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue();
        int targetArmor = MathHelper.floor(entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue() - armor);
        return currentArmor - targetArmor;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {

        //We use the entity attributes to display armor instead
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {

        if (damage > 0 && entity instanceof EntityPlayer) {
            ArmorHelper.damageArmor(stack, source, Math.max(1, damage / 4), (EntityPlayer) entity, slot);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        ResourceLocation resourceLocation = DynamicTextureHelper.getCachedTexture(stack);

        if (resourceLocation != null) {
            return resourceLocation.toString();
        } else {
            return null;
        }
    }

    public abstract String getAppearanceName();

    @SideOnly(Side.CLIENT)
    public String getArmorModifierLocation()
    {
        return "conarm:models/modifiers/mod";
    }

    @SideOnly(Side.CLIENT)
    public String getArmorModelTexture(String type)
    {
        return String.format("%s_%s", "conarm:models/armor/armor", type);
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default)
    {
        if (model == null) {
            model = new ModelConstructsArmor(armorSlot);
        }
        if (brokenModel == null) {
            brokenModel = new ModelBrokenArmor();
        }
        if (ToolHelper.isBroken(itemStack)) {
            return brokenModel;
        }
        return model;
    }

    /* Armor Information */
    public List<PartMaterialType> getRequiredComponents() {
        return ImmutableList.copyOf(requiredComponents);
    }

    public List<PartMaterialType> getArmorBuildComponents() {
        return getRequiredComponents();
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        //Make sure to never set item damage higher than max damage
        if (damage > stack.getMaxDamage()) {
            stack.setItemDamage(stack.getMaxDamage());
            return;
        }

        super.setDamage(stack, damage);
    }

    @Nonnull
    @Override
    public Entity createEntity(World world, Entity entityIn, ItemStack itemstack) {

        EntityItem entity = new IndestructibleEntityItem(world, entityIn.posX, entityIn.posY, entityIn.posZ, itemstack);

        if (entityIn instanceof EntityItem) {
            //Grabbing private field pickupDelay through NBT
            NBTTagCompound tag = new NBTTagCompound();
            entityIn.writeToNBT(tag);
            entity.setPickupDelay(tag.getShort("PickupDelay"));
        }

        entity.motionX = entityIn.motionX;
        entity.motionY = entityIn.motionY;
        entity.motionZ = entityIn.motionZ;

        return entity;
    }

    /* Building the Armor */
    public boolean validComponent(int slot, ItemStack stack) {

        return !(slot > requiredComponents.length || slot < 0) && requiredComponents[slot].isValid(stack);
    }

    /**
     * Builds an Item Stack of this armor with the given materials, if applicable.
     *
     * @param stacks Items to build with. Required to be in the correct order, have exact length, and nonnull.
     * @return The built item or null if invalid input.
     */
    @Nonnull
    public ItemStack buildItemFromStacks(NonNullList<ItemStack> stacks) {

        long itemCount = stacks.stream().filter(stack -> !stack.isEmpty()).count();
        List<Material> materials = new ArrayList<>(stacks.size());

        if (itemCount != requiredComponents.length) {
            return ItemStack.EMPTY;
        }

        for (int i = 0; i < itemCount; i++) {
            if(!validComponent(i, stacks.get(i))) {
                return ItemStack.EMPTY;
            }

            materials.add(TinkerUtil.getMaterialFromStack(stacks.get(i)));
        }

        return buildItem(materials);
    }

    /**
     * Builds an Item Stack of this armor with the given materials.
     *
     * @param materials Materials to build with. Required to be in the correct order and nonnull.
     * @return The built item or null if invalid input.
     */
    @Nonnull
    public ItemStack buildItem(List<Material> materials) {

        ItemStack armor = new ItemStack(this);
        armor.setTagCompound(buildItemNBT(materials));

        return armor;
    }

    /**
     * Builds the NBT for a new armory item with the given data.
     *
     * @param materials Materials to build with. Required to be in the correct order and nonnull.
     * @return The resulting NBT.
     */
    public NBTTagCompound buildItemNBT(List<Material> materials) {

        NBTTagCompound basetag = new NBTTagCompound();
        NBTTagCompound armorTag = buildTag(materials);
        NBTTagCompound dataTag = buildData(materials);

        basetag.setTag(Tags.BASE_DATA, dataTag);
        basetag.setTag(Tags.TOOL_DATA, armorTag);
        //Save a copy of the original armor data
        basetag.setTag(Tags.TOOL_DATA_ORIG, armorTag.copy());

        addMaterialTraits(basetag, materials);

        ArmoryEvent.OnItemBuilding.fireEvent(basetag, ImmutableList.copyOf(materials), this);

        return basetag;
    }

    /**
     * Creates an NBT Tag with the materials that were used to build the armor.
     */
    private NBTTagCompound buildData(List<Material> materials) {

        NBTTagCompound base = new NBTTagCompound();
        NBTTagList materialList = new NBTTagList();

        for (Material material : materials) {
            materialList.appendTag(new NBTTagString(material.identifier));
        }

        NBTTagList modifierList = new NBTTagList();
        modifierList.appendTag(new NBTTagString());
        modifierList.removeTag(0);

        base.setTag(Tags.BASE_MATERIALS, materialList);
        base.setTag(Tags.BASE_MODIFIERS, modifierList);

        return base;
    }

    /**
     * Builds an unusable armor that only has the rendering info.
     */
    @Nonnull
    public ItemStack buildItemForRendering(List<Material> materials) {

        ItemStack armor = new ItemStack(this);
        NBTTagCompound base = new NBTTagCompound();
        base.setTag(Tags.BASE_DATA, buildData(materials));
        armor.setTagCompound(base);

        return armor;
    }

    @Nonnull
    public ItemStack buildItemForRenderingInGui() {

        List<Material> materials = IntStream.range(0, getRequiredComponents().size())
                .mapToObj(this::getMaterialForPartForGuiRendering)
                .collect(Collectors.toList());

        return buildItemForRendering(materials);
    }

    @SideOnly(Side.CLIENT)
    public Material getMaterialForPartForGuiRendering(int index) {
        return slimeknights.tconstruct.common.ClientProxy.RenderMaterials[index % slimeknights.tconstruct.common.ClientProxy.RenderMaterials.length];
    }

    public abstract NBTTagCompound buildTag(List<Material> materials);

    public boolean hasValidMaterials(ItemStack stack) {

        NBTTagList list = TagUtil.getBaseMaterialsTagList(stack);
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(list);

        if (materials.size() != requiredComponents.length) {
            return false;
        }

        for (int i = 0; i < materials.size(); i++) {
            Material material = materials.get(i);
            PartMaterialType required = requiredComponents[i];
            if (!required.isValidMaterial(material)) {
                return false;
            }
        }

        return true;
    }

    public void addMaterialTraits(NBTTagCompound root, List<Material> materials) {

        int size = requiredComponents.length;

        if (materials.size() < size) {
            size = materials.size();
        }

        for (int i = 0; i < size; i++) {
            PartMaterialType required = requiredComponents[i];
            Material material = materials.get(i);
            for(ITrait trait : required.getApplicableTraitsForMaterial(material)) {
                ToolBuilder.addTrait(root, trait, material.materialTextColor);
            }
        }
    }

    /* Repairing */

    /** Returns indices of the parts that are used for repairing */
    public int[] getRepairParts() {
        return new int[] { 0 };
    }

    public float getRepairModifierForPart(int index) {
        return 1f;
    }

    @Nonnull
    @Override
    public ItemStack repair(ItemStack repairable, NonNullList<ItemStack> repairItems) {

        if (repairable.getItemDamage() == 0 && !ToolHelper.isBroken(repairable)) {
            return ItemStack.EMPTY;
        }

        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(repairable));

        if (materials.isEmpty()) {
            return ItemStack.EMPTY;
        }

        NonNullList<ItemStack> items = Util.deepCopyFixedNonNullList(repairItems);
        boolean foundMatch = false;

        for (int index : getRepairParts()) {
            Material material = materials.get(index);

            if(repairCustom(material, items) > 0) {
                foundMatch = true;
            }

            Optional<RecipeMatch.Match> match = material.matches(items);

            if(!match.isPresent()) {
                continue;
            }
            foundMatch = true;

            while((match = material.matches(items)).isPresent()) {
                RecipeMatch.removeMatch(items, match.get());
            }
        }

        if(!foundMatch) {
            return ItemStack.EMPTY;
        }

        for(int i = 0; i < repairItems.size(); i++) {
            if(!repairItems.get(i).isEmpty() && ItemStack.areItemStacksEqual(repairItems.get(i), items.get(i))) {
                return ItemStack.EMPTY;
            }
        }

        ItemStack item = repairable.copy();

        while(item.getItemDamage() > 0) {
            int amount = calculateRepairAmount(materials, repairItems, EntityLiving.getSlotForItemStack(repairable));

            if(amount <= 0) {
                break;
            }

            ArmorHelper.repairArmor(item, calculateRepair(item, amount));
            NBTTagCompound tag = TagUtil.getExtraTag(item);
            tag.setInteger(Tags.REPAIR_COUNT, tag.getInteger(Tags.REPAIR_COUNT) + 1);
            TagUtil.setExtraTag(item, tag);
        }

        return item;
    }

    /** Allows for custom repair items. Remove used items from the array. */
    protected int repairCustom(Material material, NonNullList<ItemStack> repairItems) {
        return 0;
    }

    protected int calculateRepairAmount(List<Material> materials, NonNullList<ItemStack> repairItems, EntityEquipmentSlot slotIn) {
        Set<Material> materialsMatched = Sets.newHashSet();
        float durability = 0f;
        for(int index : getRepairParts()) {
            Material material = materials.get(index);

            if(materialsMatched.contains(material)) {
                continue;
            }

            durability += repairCustom(material, repairItems) * ArmorHelper.durabilityMultipliers[slotIn.getIndex()] * getRepairModifierForPart(index);

            Optional<RecipeMatch.Match> matchOptional = material.matches(repairItems);
            if(matchOptional.isPresent()) {
                RecipeMatch.Match match = matchOptional.get();
                CoreMaterialStats stats = material.getStats(ArmorMaterialType.CORE);
                if(stats != null) {
                    materialsMatched.add(material);
                    durability += (stats.durability * ArmorHelper.durabilityMultipliers[slotIn.getIndex()] * (float) match.amount * getRepairModifierForPart(index)) / 144F;
                    RecipeMatch.removeMatch(repairItems, match);
                }
            }
        }

        durability *= 1f + ((float) materialsMatched.size() - 1) / 9f;

        return (int) durability;
    }

    protected int calculateRepair(ItemStack tool, int amount) {
        float origDur = TagUtil.getOriginalToolStats(tool).durability;
        float actualDur = ToolHelper.getDurabilityStat(tool);

        float durabilityFactor = actualDur / origDur;
        float increase = amount * Math.min(10f, durabilityFactor);

        increase = Math.max(increase, actualDur / 64f);

        int modifiersUsed = TagUtil.getBaseModifiersUsed(tool.getTagCompound());
        float mods = 1.0f;
        if(modifiersUsed == 1) {
            mods = 0.95f;
        }
        else if(modifiersUsed == 2) {
            mods = 0.9f;
        }
        else if(modifiersUsed >= 3) {
            mods = 0.85f;
        }

        increase *= mods;

        NBTTagCompound tag = TagUtil.getExtraTag(tool);
        int repair = tag.getInteger(Tags.REPAIR_COUNT);
        float repairDimishingReturns = (100 - repair / 2) / 100f;
        if(repairDimishingReturns < 0.5f) {
            repairDimishingReturns = 0.5f;
        }
        increase *= repairDimishingReturns;

        return (int) Math.ceil(increase);
    }

    /* Tooltip Information */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        boolean shift = Util.isShiftKeyDown();
        boolean ctrl = Util.isCtrlKeyDown();

        if (!shift && !ctrl) {
            getTooltip(stack, tooltip);

            tooltip.add("");
            tooltip.add(Util.translate("tooltip.tool.holdShift"));
            tooltip.add(Util.translate("tooltip.tool.holdCtrl"));
        }
        else if (Config.extraTooltips && shift) {
            getTooltipDetailed(stack, tooltip);
        }
        else if (Config.extraTooltips && ctrl) {
            getTooltipComponents(stack, tooltip);
        }
    }

    @Override
    public void getTooltip(ItemStack stack, List<String> tooltips) {
        ArmorTooltipBuilder.addModifierTooltips(stack, tooltips);
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack) { return false; }

    /* NBT Loading */
    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {

        //Recalculate the data when the NBT is loaded in
        if(nbt.hasKey(Tags.BASE_DATA)) {
            try {
                ArmorBuilder.rebuildArmor(nbt, this);
            }
            catch(TinkerGuiException e) {
                // NO-OP
            }
        }

        return true;
    }
}
