/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.constructsarmory.common.modifier.trait.general;

import javax.annotation.Nonnull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolRebuildContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import com.illusivesoulworks.constructsarmory.common.stat.ConstructsArmoryStats;

public class DuctileModifier extends Modifier {

  @Override
  public void addToolStats(@Nonnull ToolRebuildContext context, int level,
                           @Nonnull ModifierStatsBuilder builder) {
    ToolStats.DURABILITY.multiply(builder, 1 + (level * 0.04f));
    ToolStats.ARMOR.multiply(builder, 1 + (level * 0.04f));
    ToolStats.ARMOR_TOUGHNESS.multiply(builder, 1 + (level * 0.04f));
    ToolStats.KNOCKBACK_RESISTANCE.multiply(builder, 1 + (level * 0.04f));
    ConstructsArmoryStats.MOVEMENT_SPEED.multiply(builder, 1 + (level * 0.04f));
  }
}
