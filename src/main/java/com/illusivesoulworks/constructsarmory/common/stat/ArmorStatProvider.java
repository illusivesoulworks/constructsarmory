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

import java.util.List;
import javax.annotation.Nonnull;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.IToolStatProvider;
import slimeknights.tconstruct.library.tools.definition.PartRequirement;
import slimeknights.tconstruct.library.tools.definition.ToolDefinitionData;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import com.illusivesoulworks.constructsarmory.common.stat.impl.MailMaterialStats;
import com.illusivesoulworks.constructsarmory.common.stat.impl.PlateMaterialStats;

public class ArmorStatProvider implements IToolStatProvider {

  private final ArmorSlotType slotType;

  public ArmorStatProvider(ArmorSlotType slotType) {
    this.slotType = slotType;
  }

  @Nonnull
  @Override
  public StatsNBT buildStats(@Nonnull ToolDefinition definition,
                             @Nonnull List<IMaterial> materials) {
    return ArmorStatsBuilder.from(this.slotType, definition, materials).buildStats();
  }

  @Override
  public boolean isMultipart() {
    return true;
  }

  @Override
  public void validate(ToolDefinitionData data) {
    List<PartRequirement> requirements = data.getParts();

    if (requirements.isEmpty()) {
      throw new IllegalStateException("Must have at least one armor part for an armor piece");
    }
    boolean foundPlate = false;

    for (PartRequirement req : requirements) {
      MaterialStatsId statType = req.getStatType();

      if (statType.equals(PlateMaterialStats.ID)) {
        foundPlate = true;
      } else if (!statType.equals(MailMaterialStats.ID)) {
        throw new IllegalStateException(
            "Invalid armor part type, only supports plate and mail part types");
      }
    }

    if (!foundPlate) {
      throw new IllegalStateException("Armor piece must use at least one plate part");
    }
  }
}
