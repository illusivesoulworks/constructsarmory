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

package c4.conarm.lib.armor;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.CoreMaterialStats;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.LocUtils;
import slimeknights.tconstruct.common.ClientProxy;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ArmorPart extends MaterialItem implements IToolPart
{
    protected int cost;
    protected EntityEquipmentSlot slot;

    public ArmorPart(int cost) {
        this.setCreativeTab(TinkerRegistry.tabParts);
        this.cost = cost;
    }

    public ArmorPart(int cost, EntityEquipmentSlot slot) {
        this(cost);
        this.slot = slot;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public EntityEquipmentSlot getSlot() { return slot; }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList< ItemStack > subItems) {
        if(this.isInCreativeTab(tab)) {
            for(Material mat : TinkerRegistry.getAllMaterials()) {
                if(canUseMaterial(mat)) {
                    subItems.add(getItemstackWithMaterial(mat));
                    if(!Config.listAllMaterials) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean canUseMaterial(Material mat) {
        for(ArmorCore armor : ArmoryRegistry.getArmor()) {
            for(PartMaterialType pmt : armor.getRequiredComponents()) {
                if(pmt.isValid(this, mat)) {
                    return true;
                }
            }
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Material material = getMaterial(stack);

        boolean shift = Util.isShiftKeyDown();

        if(!checkMissingMaterialTooltip(stack, tooltip)) {
            tooltip.addAll(getTooltipTraitInfo(material));
        }

        if(Config.extraTooltips) {
            if(!shift) {
                tooltip.add("");
                tooltip.add(Util.translate("tooltip.tool.holdShift"));
            }
            else {
                tooltip.addAll(getTooltipStatsInfo(material));
            }
        }

        tooltip.addAll(getAddedByInfo(material));
    }

    public List<String> getTooltipTraitInfo(Material material) {
        Map<String, List<ITrait>> mapping = Maps.newConcurrentMap();

        for(IMaterialStats stat : material.getAllStats()) {
            if(hasUseForStat(stat.getIdentifier())) {
                List<ITrait> traits = material.getAllTraitsForStats(stat.getIdentifier());
                if(!traits.isEmpty()) {
                    boolean unified = false;
                    for(Map.Entry<String, List<ITrait>> entry : mapping.entrySet()) {
                        if(entry.getValue().equals(traits)) {
                            mapping.put(entry.getKey() + ", " + stat.getLocalizedName(), entry.getValue());
                            mapping.remove(entry.getKey());
                            unified = true;
                            break;
                        }
                    }

                    if(!unified) {
                        mapping.put(stat.getLocalizedName(), traits);
                    }
                }
            }
        }

        List<String> tooltips = Lists.newLinkedList();
        boolean withType = mapping.size() > 1;

        for(Map.Entry<String, List<ITrait>> entry : mapping.entrySet()) {
            StringBuilder sb = new StringBuilder();
            if(withType) {
                sb.append(TextFormatting.ITALIC.toString());
                sb.append(entry.getKey());
                sb.append(": ");
                sb.append(TextFormatting.RESET.toString());
            }
            sb.append(material.getTextColor());
            List<ITrait> traits = entry.getValue();
            if(!traits.isEmpty()) {
                ListIterator<ITrait> iter = traits.listIterator();

                sb.append(iter.next().getLocalizedName());
                while(iter.hasNext()) {
                    sb.append(", ").append(iter.next().getLocalizedName());
                }

                tooltips.add(sb.toString());
            }
        }

        return tooltips;
    }

    public List<String> getTooltipStatsInfo(Material material) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for(IMaterialStats stat : material.getAllStats()) {
            if(hasUseForStat(stat.getIdentifier())) {
                List<String> text = stat.getLocalizedInfo();
                if(!text.isEmpty()) {
                    builder.add("");
                    builder.add(TextFormatting.WHITE.toString() + TextFormatting.UNDERLINE + stat.getLocalizedName());
                    builder.addAll(text);
                }
            }
        }

        return builder.build();
    }

    public List<String> getAddedByInfo(Material material) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        if(TinkerRegistry.getTrace(material) != null) {
            String materialInfo = Util.translateFormatted("tooltip.part.material_added_by",
                    TinkerRegistry.getTrace(material).getName());
            builder.add("");
            builder.add(materialInfo);
        }
        return builder.build();
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        Material material = getMaterial(stack);

        String locString = getTranslationKey() + "." + material.getIdentifier();

        if(I18n.canTranslate(locString)) {
            return Util.translate(locString);
        }

        return material.getLocalizedItemName(super.getItemStackDisplayName(stack));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public FontRenderer getFontRenderer(ItemStack stack) {
        return ClientProxy.fontRenderer;
    }

    @Override
    public boolean hasUseForStat(String stat) {
        for(ArmorCore armor : ArmoryRegistry.getArmor()) {
            for(PartMaterialType amt : armor.getRequiredComponents()) {
                if(amt.isValidItem(this) && amt.usesStat(stat)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkMissingMaterialTooltip(ItemStack stack, List<String> tooltip) {
        return checkMissingMaterialTooltip(stack, tooltip, null);
    }

    public boolean checkMissingMaterialTooltip(ItemStack stack, List<String> tooltip, String statIdentifier) {
        Material material = getMaterial(stack);

        if(material == Material.UNKNOWN) {
            NBTTagCompound tag = TagUtil.getTagSafe(stack);
            String materialID = tag.getString(Tags.PART_MATERIAL);

            String error;
            if(!materialID.isEmpty()) {
                error = Util.translateFormatted("tooltip.part.missing_material", materialID);
            }
            else {
                error = Util.translate("tooltip.part.missing_info");
            }
            tooltip.addAll(LocUtils.getTooltips(error));
            return true;
        }
        else if(statIdentifier != null && material.getStats(statIdentifier) == null) {
            tooltip.addAll(LocUtils.getTooltips(Util.translateFormatted("tooltip.part.missing_stats", material.getLocalizedName(), statIdentifier)));
            return true;
        }

        return false;
    }
}
