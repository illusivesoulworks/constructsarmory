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

package com.illusivesoulworks.constructsarmory.api;

import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;

public class ArmorMaterialStatsIdentifiers {

  public static final MaterialStatsId PLATE =
      new MaterialStatsId(ConstructsArmoryMod.getResource("plate"));
  public static final MaterialStatsId MAIL =
      new MaterialStatsId(ConstructsArmoryMod.getResource("mail"));
}
