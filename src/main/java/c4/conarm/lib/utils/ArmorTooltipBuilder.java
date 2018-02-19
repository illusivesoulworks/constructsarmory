package c4.conarm.lib.utils;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.utils.TooltipBuilder;

public class ArmorTooltipBuilder {

    public static TooltipBuilder addArmor(TooltipBuilder info, ItemStack stack) {

        info.add(CoreMaterialStats.formatArmor(ArmorHelper.getActualArmor(stack)));

        return info;
    }

    public static TooltipBuilder addToughness(TooltipBuilder info, ItemStack stack) {

        info.add(PlatesMaterialStats.formatToughness(ArmorHelper.getActualToughness(stack)));

        return info;
    }
}
