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

package c4.conarm.lib.materials;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;

import java.util.List;

public class PlatesMaterialStats extends AbstractMaterialStats {

    public final static String LOC_Durability = "stat.plates.durability.name";
    public final static String LOC_Modifier = "stat.plates.modifier.name";
    public final static String LOC_Toughness = "stat.plates.toughness.name";

    public final static String LOC_DurabilityDesc = "stat.plates.durability.desc";
    public final static String LOC_ModifierDesc = "stat.plates.modifier.desc";
    public final static String LOC_ToughnessDesc = "stat.plates.toughness.desc";

    public final static String COLOR_Durability = CoreMaterialStats.COLOR_Durability;
    public final static String COLOR_Modifier = CustomFontColor.encodeColor(185, 185, 90);
    public final static String COLOR_Toughness = CustomFontColor.encodeColor(120, 160, 205);

    public final float durability;
    public final float modifier;
    public final float toughness;

    public PlatesMaterialStats(float modifier, float durability, float toughness) {
        super(ArmorMaterialType.PLATES);
        this.durability = durability;
        this.modifier = modifier;
        this.toughness = toughness;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();

        info.add(formatModifier(modifier));
        info.add(formatDurability(durability));
        info.add(formatToughness(toughness));

        return info;
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_ModifierDesc));
        info.add(Util.translate(LOC_DurabilityDesc));
        info.add(Util.translate(LOC_ToughnessDesc));

        return info;
    }

    public static String formatToughness(float toughness) {
        return formatNumber(LOC_Toughness, COLOR_Toughness, toughness);
    }

    public static String formatModifier(float quality) {
        return formatNumber(LOC_Modifier, COLOR_Modifier, quality);
    }

    public static String formatDurability(float durability) {
        return formatNumber(LOC_Durability, COLOR_Durability, durability);
    }
}
