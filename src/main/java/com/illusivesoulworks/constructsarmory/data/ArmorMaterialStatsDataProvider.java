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

package com.illusivesoulworks.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import com.illusivesoulworks.constructsarmory.common.stat.impl.MailMaterialStats;
import com.illusivesoulworks.constructsarmory.common.stat.impl.PlateMaterialStats;

public class ArmorMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {

  public ArmorMaterialStatsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialStats() {

    // Tier 1
    addMaterialStats(MaterialIds.wood, 45, 2.0f, 0.0f, 0.0f, 0.0075f, 1.0f, 1.0f, 1.0f);
    addMaterialStats(MaterialIds.rock, 121, 3.0f, 0.0f, 0.025f, 0.005f, 0.8f, 1.05f, 1.0f);
    addMaterialStats(MaterialIds.leather, 80, 4.0f, 0.0f, 0.0f, 0.0375f, 0.8f, 1.0f, 1.1f);
    addMaterialStats(MaterialIds.bone, 95, 5.0f, 1.0f, 0.0f, 0.0150f, 0.75f, 1.1f, 1.0f);
    addMailStats(MaterialIds.vine, 0.85f, 1.0f, 1.05f);

    // Tier 2
    addMaterialStats(MaterialIds.iron, 240, 14.0f, 0.0f, 0.0f, 0.0125f, 1.1f, 1.0f, 1.0f);
    addMaterialStats(MaterialIds.copper, 200, 10.0f, 0.0f, 0.0f, 0.045f, 0.85f, 1.0f, 1.15f);
    addMaterialStats(MaterialIds.searedStone, 211, 13.0f, 0.0f, 0.05f, 0.01f, 0.85f, 1.15f, 1.0f);
    addMaterialStats(MaterialIds.slimewood, 309, 8.0f, 2.0f, 0.08f, 0.02f, 1.3f, 0.8f, 1.0f);
    addMaterialStats(MaterialIds.necroticBone, 153, 12.0f, 2.0f, 0.0f, 0.015f, 0.7f, 1.15f, 1.1f);
    addMaterialStats(MaterialIds.bloodbone, 172, 11.0f, 2.0f, 0.0f, 0.025f, 0.9f, 1.1f, 1.05f);
    addMailStats(MaterialIds.skyslimeVine, 1.2f, 0.85f, 1.0f);

    // Tier 2 - Addons
    addMaterialStats(MaterialIds.osmium, 345, 10.0f, 0.0f, 0.05f, 0.025f, 1.2f, 1.05f, 0.9f);
    addMaterialStats(MaterialIds.tungsten, 295, 13.0f, 1.0f, 0.05f, 0.005f, 0.9f, 1.2f, 0.95f);
    addMaterialStats(MaterialIds.platinum, 323, 11.0f, 0.0f, 0.0f, 0.035f, 1.05f, 0.95f, 1.05f);
    addMaterialStats(MaterialIds.silver, 257, 13.0f, 1.0f, 0.0f, 0.0125f, 0.9f, 1.1f, 1.0f);
    addMaterialStats(MaterialIds.lead, 198, 14.0f, 1.0f, 0.05f, 0.0025f, 0.9f, 1.25f, 0.9f);
    addMaterialStats(MaterialIds.whitestone, 249, 12.0f, 0.0f, 0.05f, 0.02f, 1.05f, 1.1f, 0.95f);

    // Tier 3
    addMaterialStats(MaterialIds.slimesteel, 502, 11.0f, 3.0f, 0.125f, 0.025f, 1.2f, 0.9f, 0.95f);
    addMaterialStats(MaterialIds.bronze, 437, 12.0f, 2.0f, 0.05f, 0.055f, 1.05f, 1.0f, 1.1f);
    addMaterialStats(MaterialIds.nahuatl, 275, 15.0f, 2.0f, 0.08f, 0.01f, 0.9f, 1.25f, 0.95f);
    addMaterialStats(MaterialIds.pigIron, 386, 14.0f, 2.0f, 0.05f, 0.03f, 1.1f, 1.05f, 1.0f);
    addMaterialStats(MaterialIds.roseGold, 182, 11.0f, 0.0f, 0.0f, 0.07f, 0.6f, 1.0f, 1.45f);
    addMaterialStats(MaterialIds.cobalt, 480, 12.0f, 2.0f, 0.1f, 0.05f, 1.05f, 0.9f, 1.15f);

    // Tier 3 - Addons
    addMaterialStats(MaterialIds.steel, 474, 13.0f, 2.0f, 0.1f, 0.025f, 1.05f, 1.1f, 1.0f);
    addMaterialStats(MaterialIds.constantan, 428, 12.0f, 2.0f, 0.08f, 0.05f, 0.95f, 1.0f, 1.15f);
    addMaterialStats(MaterialIds.invar, 400, 15.0f, 2.0f, 0.05f, 0.0125f, 1.0f, 1.15f, 1.0f);
    addMaterialStats(MaterialIds.necronium, 279, 14.0f, 3.0f, 0.0f, 0.02f, 0.8f, 1.2f, 1.1f);
    addMaterialStats(MaterialIds.electrum, 227, 12.0f, 0.0f, 0.0f, 0.065f, 0.8f, 1.0f, 1.3f);
    addMaterialStats(MaterialIds.platedSlimewood, 393, 10.0f, 3.0f, 0.1f, 0.025f, 1.25f, 0.95f,
        0.9f);

    // Tier 4
    addMaterialStats(MaterialIds.queensSlime, 549, 12.0f, 4.0f, 0.15f, 0.03f, 1.35f, 0.9f, 0.95f);
    addMaterialStats(MaterialIds.hepatizon, 496, 12.0f, 3.0f, 0.1f, 0.0625f, 1.1f, 0.9f, 1.2f);
    addMaterialStats(MaterialIds.manyullyn, 521, 16.0f, 3.0f, 0.125f, 0.01f, 1.1f, 1.2f, 0.85f);
    addMaterialStats(MaterialIds.blazingBone, 356, 14.0f, 3.0f, 0.0f, 0.035f, 0.85f, 1.15f, 1.1f);

    // Tier 5
    addMailStats(MaterialIds.enderslimeVine, 1.3f, 0.95f, 1.0f);
  }

  protected void addMailStats(MaterialId id, float durabilityMultiplier, float armorMultiplier,
                              float movementMultiplier) {
    addMaterialStats(id,
        new MailMaterialStats(durabilityMultiplier, armorMultiplier, movementMultiplier));
  }

  protected void addMaterialStats(MaterialId id, int durability, float armor, float toughness,
                                  float knockbackResistance, float movementSpeed,
                                  float durabilityMultiplier, float armorMultiplier,
                                  float movementMultiplier) {
    //TODO: For some reasons when the plate is added, it throws an IllegalArgumentException because it's duplicated
    addMaterialStats(id,
        new PlateMaterialStats(durability, armor, toughness, knockbackResistance, movementSpeed),
        new MailMaterialStats(durabilityMultiplier, armorMultiplier, movementMultiplier));
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Stats";
  }
}
