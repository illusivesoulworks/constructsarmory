package c4.conarm.lib.armor;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import c4.conarm.lib.utils.ArmorTooltipBuilder;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

    public ArmorCore(EntityEquipmentSlot slotIn, PartMaterialType... requiredComponents) {
        super(slotIn, requiredComponents);

        this.setCreativeTab(TinkerRegistry.tabTools);
        this.setNoRepair();
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
        ArmorTooltipBuilder.addArmor(info, stack);
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
                        if (stats instanceof CoreMaterialStats) {
                            tooltips.addAll(((CoreMaterialStats) stats).getLocalizedInfo(armorType));
                        } else {
                            tooltips.addAll(stats.getLocalizedInfo());
                        }
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

    public String getIdentifier() {
        return getRegistryName().getResourcePath();
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

    protected abstract ArmorNBT buildTagData(List<Material> materials);

    @Override
    public void getTooltipDetailed(ItemStack stack, List<String> tooltips) {
        tooltips.addAll(getInformation(stack, false));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {

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

        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, armor);
        NBTTagList traitsList = TagUtil.getTraitsTagList(armor);

        for(int i = 0; i < traitsList.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(traitsList.getStringTagAt(i));
            if(trait != null) {
                trait.getAttributeModifiers(slot, armor, multimap);
            }
        }

        if (multimap.isEmpty()) {
            multimap.clear();
        }

        return multimap;
    }
}
