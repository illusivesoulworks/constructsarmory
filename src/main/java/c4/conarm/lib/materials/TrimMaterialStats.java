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

import com.google.common.collect.ImmutableList;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;

import java.util.List;

public class TrimMaterialStats extends AbstractMaterialStats {

    public final static String LOC_Durability = "stat.trim.durability.name";
    public final static String LOC_DurabilityDesc = "stat.trim.durability.desc";
    public final static String COLOR_Durability = CoreMaterialStats.COLOR_Durability;

    public final float extraDurability;

    public TrimMaterialStats(float extraDurability) {
        super(ArmorMaterialType.TRIM);
        this.extraDurability = extraDurability;
    }

    @Override
    public List<String> getLocalizedInfo() {
        return ImmutableList.of(formatDurability(extraDurability));
    }

    @Override
    public List<String> getLocalizedDesc() {
        return ImmutableList.of(Util.translate(LOC_DurabilityDesc));
    }

    public static String formatDurability(float durability) {
        return formatNumber(LOC_Durability, COLOR_Durability, durability);
    }
}
