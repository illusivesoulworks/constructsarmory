/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
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
