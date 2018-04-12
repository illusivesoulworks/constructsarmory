package c4.conarm.lib.utils;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.utils.TooltipBuilder;

public class ArmorTooltipBuilder {

    public static TooltipBuilder addDefense(TooltipBuilder info, ItemStack stack) {

        info.add(CoreMaterialStats.formatDefense(ArmorHelper.getDefense(stack)));

        return info;
    }

    public static TooltipBuilder addToughness(TooltipBuilder info, ItemStack stack) {

        info.add(PlatesMaterialStats.formatToughness(ArmorHelper.getToughness(stack)));

        return info;
    }
}
