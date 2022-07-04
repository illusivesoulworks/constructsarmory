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

package com.illusivesoulworks.constructsarmory.common.stat;

import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;

public class ConstructsArmoryStats {

  public static final FloatToolStat MOVEMENT_SPEED =
      ToolStats.register(new FloatToolStat(name("movement_speed"), 0xff78a0cd, 0.01f, 0f, 2048f));

  public static void init() {
    // NO-OP
  }

  private static ToolStatId name(String name) {
    return new ToolStatId(ConstructsArmoryMod.MOD_ID, name);
  }
}
