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

public class CoreMaterialStats extends AbstractMaterialStats {

    public final static String LOC_Durability = "stat.core.durability.name";
    public final static String LOC_Defense = "stat.core.defense.name";

    public final static String LOC_DurabilityDesc = "stat.core.durability.desc";
    public final static String LOC_DefenseDesc = "stat.core.defense.desc";

    public final static String COLOR_Durability = CustomFontColor.valueToColorCode(1f);
    public final static String COLOR_Defense = CustomFontColor.encodeColor(215, 100, 100);

    public final float durability;
    public final float defense;

    public CoreMaterialStats(float durability, float defense) {
        super(ArmorMaterialType.CORE);
        this.durability = durability;
        this.defense = defense;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();

        info.add(formatDurability(durability));
        info.add(formatDefense(defense));

        return info;
    }

    public static String formatDurability(float durability) {
        return formatNumber(LOC_Durability, COLOR_Durability, durability);
    }

    public static String formatDefense(float defense) {
        return formatNumber(LOC_Defense, COLOR_Defense, defense);
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_DurabilityDesc));
        info.add(Util.translate(LOC_DefenseDesc));

        return info;
    }
}
