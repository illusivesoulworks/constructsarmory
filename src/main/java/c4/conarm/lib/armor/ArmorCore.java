/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.lib.armor;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import c4.conarm.lib.tinkering.ArmorTooltipBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.IToolStationDisplay;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.library.utils.TooltipBuilder;

import javax.annotation.Nonnull;
import java.util.*;

/*This class is a re-implementation of the
ToolCore class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public abstract class ArmorCore extends TinkersArmor implements IToolStationDisplay {

    public final static int DEFAULT_MODIFIERS = 3;
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    private final String appearanceName;

    public ArmorCore(EntityEquipmentSlot slotIn, String appearanceName, PartMaterialType core) {
        super(slotIn, core, ArmorMaterialType.plating(ConstructsRegistry.armorPlate), ArmorMaterialType.trim(ConstructsRegistry.armorTrim));

        this.setCreativeTab(TinkerRegistry.tabTools);
        this.setNoRepair();
        this.appearanceName = appearanceName;

        ArmoryRegistry.addArmor(this, slotIn);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ToolHelper.getDurabilityStat(stack);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return super.showDurabilityBar(stack) && !ToolHelper.isBroken(stack);
    }

    @Override
    public String getAppearanceName() {
        return this.appearanceName;
    }

    public String getLocalizedAppearanceName() {
        return Util.translate("appearance." + this.getAppearanceName() + ".name");
    }

    @Override
    public String getLocalizedToolName() {
        return Util.translate(this.getUnlocalizedName() + ".name");
    }

    public String getLocalizedDescription() {
        return Util.translate(this.getUnlocalizedName() + ".desc");
    }

    @Override
    public List<String> getInformation(ItemStack stack) {
        return getInformation(stack, true);
    }

    public List<String> getInformation(ItemStack stack, boolean detailed) {

        TooltipBuilder info = new TooltipBuilder(stack);

        info.addDurability(!detailed);
        ArmorTooltipBuilder.addDefense(info, stack);
        ArmorTooltipBuilder.addToughness(info, stack);

        if (ToolHelper.getFreeModifiers(stack) > 0) {
            info.addFreeModifiers();
        }

        if (detailed) {
            info.addModifierInfo();
        }

        return info.getTooltip();
    }

    @Override
    public void getTooltipComponents(ItemStack stack, List<String> tooltips) {

        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));
        List<PartMaterialType> component = getRequiredComponents();

        if (materials.size() < component.size()) {
            return;
        }

        for (int i = 0; i < component.size(); i++) {
            PartMaterialType pmt = component.get(i);
            Material material = materials.get(i);

            Iterator<IToolPart> partIter = pmt.getPossibleParts().iterator();
            if (!partIter.hasNext()) {
                continue;
            }

            IToolPart part = partIter.next();
            ItemStack partStack = part.getItemstackWithMaterial(material);
            if (partStack != null) {
                tooltips.add(material.getTextColor() + TextFormatting.UNDERLINE + partStack.getDisplayName());

                Set<ITrait> usedTraits = Sets.newHashSet();
                for(IMaterialStats stats : material.getAllStats()) {
                    if(pmt.usesStat(stats.getIdentifier())) {
                        tooltips.addAll(stats.getLocalizedInfo());
                        for(ITrait trait : pmt.getApplicableTraitsForMaterial(material)) {
                            if(!usedTraits.contains(trait)) {
                                tooltips.add(material.getTextColor() + trait.getLocalizedName());
                                usedTraits.add(trait);
                            }
                        }
                    }
                }
                tooltips.add("");
            }
        }
    }

    @Override
    protected int repairCustom(Material material, NonNullList<ItemStack> repairItems) {
        Optional<RecipeMatch.Match> matchOptional = RecipeMatch.of(ConstructsRegistry.polishingKit).matches(repairItems);
        if(!matchOptional.isPresent()) {
            return 0;
        }

        RecipeMatch.Match match = matchOptional.get();
        for(ItemStack stacks : match.stacks) {
            // invalid material?
            if(ConstructsRegistry.polishingKit.getMaterial(stacks) != material) {
                return 0;
            }
        }

        RecipeMatch.removeMatch(repairItems, match);
        CoreMaterialStats stats = material.getStats(ArmorMaterialType.CORE);
        float durability = stats.durability * match.amount * ConstructsRegistry.polishingKit.getCost();
        durability /= Material.VALUE_Ingot;
        return (int) (durability);
    }

    public String getIdentifier() {
        return getRegistryName().getResourcePath();
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        this.onUpdateTraits(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    private void onUpdateTraits(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        NBTTagList list = TagUtil.getTraitsTagList(stack);
        for(int i = 0; i < list.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
            if(trait != null) {
                trait.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
            }
        }
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public FontRenderer getFontRenderer(ItemStack stack) {
        return slimeknights.tconstruct.common.ClientProxy.fontRenderer;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return TagUtil.hasEnchantEffect(stack);
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {

        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));
        //Save all of the materials in the name in a set to avoid redundancies
        Set<Material> nameMaterials = Sets.newLinkedHashSet();

        for (int index : getRepairParts()) {
            if(index < materials.size()) {
                nameMaterials.add(materials.get(index));
            }
        }

        return Material.getCombinedItemName(super.getItemStackDisplayName(stack), nameMaterials);
    }

    protected ArmorNBT buildDefaultTag(List<Material> materials, int slotIn) {

        ArmorNBT data = new ArmorNBT();

        if (materials.size() >= 2) {
            CoreMaterialStats core = materials.get(0).getStatsOrUnknown(ArmorMaterialType.CORE);
            PlatesMaterialStats plating = materials.get(1).getStatsOrUnknown(ArmorMaterialType.PLATES);
            data.core(slotIn, core);

            if (materials.size() >= 3) {
                TrimMaterialStats trim = materials.get(2).getStatsOrUnknown(ArmorMaterialType.TRIM);
                data.trim(slotIn, trim);
            }

            data.plating(slotIn, plating);
        }

        data.modifiers = DEFAULT_MODIFIERS;

        return data;
    }

    @Override
    public final NBTTagCompound buildTag(List<Material> materials) {
        return buildTagData(materials).get();
    }

    protected ArmorNBT buildTagData(List<Material> materials) {

        return buildDefaultTag(materials, armorType.getIndex());
    }

    @Override
    public void getTooltipDetailed(ItemStack stack, List<String> tooltips) {
        tooltips.addAll(getInformation(stack, false));
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {

        if (this.isInCreativeTab(tab)) {
            addDefaultSubItems(subItems);
        }
    }

    protected void addDefaultSubItems(List<ItemStack> subItems, Material... fixedMaterials) {

        for (Material core : TinkerRegistry.getAllMaterials()) {
            List<Material> mats = new ArrayList<>(requiredComponents.length);

            for (int i = 0; i < requiredComponents.length; i++) {
                if(fixedMaterials.length > i && fixedMaterials[i] != null && requiredComponents[i].isValidMaterial(fixedMaterials[i])) {
                    mats.add(fixedMaterials[i]);
                }
                else {
                    mats.add(core);
                }
            }

            ItemStack armor = buildItem(mats);
            if(hasValidMaterials(armor)) {
                subItems.add(armor);
                if(!Config.listAllMaterials) {
                    break;
                }
            }
        }
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack armor) {

        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (!ToolHelper.isBroken(armor)) {
            if (slot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", ArmorHelper.getArmor(armor, slot.getIndex()), 0));
                multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", ArmorHelper.getToughness(armor), 0));
            }

            NBTTagList traitsList = TagUtil.getTraitsTagList(armor);

            for (int i = 0; i < traitsList.tagCount(); i++) {
                ITrait trait = TinkerRegistry.getTrait(traitsList.getStringTagAt(i));
                if (trait != null) {
                    trait.getAttributeModifiers(slot, armor, multimap);
                }
            }
        }

        return multimap;
    }
}
