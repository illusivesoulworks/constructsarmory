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
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryModifiers;
import com.illusivesoulworks.constructsarmory.common.stat.impl.MailMaterialStats;
import com.illusivesoulworks.constructsarmory.common.stat.impl.PlateMaterialStats;

public class ArmorMaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

  public ArmorMaterialTraitsDataProvider(DataGenerator gen,
                                         AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialTraits() {
    // Tier 1
    addArmorTraits(MaterialIds.wood, ConstructsArmoryModifiers.CULTIVATED.get());
    addArmorTraits(MaterialIds.rock, ConstructsArmoryModifiers.PETROUS.get());
    addArmorTraits(MaterialIds.leather, ConstructsArmoryModifiers.WOVEN.get());
    addArmorTraits(MaterialIds.bone, ConstructsArmoryModifiers.SPLINTERED.get());
    addTraits(MaterialIds.vine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.SOLAR_POWERED.get().getId());

    // Tier 2
    addArmorTraits(MaterialIds.iron, ConstructsArmoryModifiers.REINFORCED.get());
    addArmorTraits(MaterialIds.slimewood, ConstructsArmoryModifiers.OVERGROWTH.get());
    addArmorTraits(MaterialIds.copper, ConstructsArmoryModifiers.DELVING.get());
    addArmorTraits(MaterialIds.searedStone, ConstructsArmoryModifiers.IGNEOUS.get());
    addArmorTraits(MaterialIds.necroticBone, ConstructsArmoryModifiers.MALIGNANT.get());
    addArmorTraits(MaterialIds.bloodbone, ConstructsArmoryModifiers.BLOODLETTING.get());
    addTraits(MaterialIds.skyslimeVine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.AERIAL.get().getId());

    // Tier 2 Addons
    addArmorTraits(MaterialIds.osmium, ConstructsArmoryModifiers.DENSE.get());
    addArmorTraits(MaterialIds.tungsten, ConstructsArmoryModifiers.WEIGHTY.get());
    addArmorTraits(MaterialIds.platinum, ConstructsArmoryModifiers.RADIANT.get());
    addArmorTraits(MaterialIds.silver, ConstructsArmoryModifiers.HALLOWED.get());
    addArmorTraits(MaterialIds.lead, ConstructsArmoryModifiers.SHIELDING.get());
    addArmorTraits(MaterialIds.whitestone, ConstructsArmoryModifiers.STONEGUARD.get());

    // Tier 3
    addArmorTraits(MaterialIds.slimesteel, ConstructsArmoryModifiers.OVERCAST.get());
    addArmorTraits(MaterialIds.bronze, ConstructsArmoryModifiers.IMMACULATE.get());
    addArmorTraits(MaterialIds.cobalt, ConstructsArmoryModifiers.NIMBLE.get());
    addArmorTraits(MaterialIds.pigIron, ConstructsArmoryModifiers.SAVORY.get());
    addArmorTraits(MaterialIds.nahuatl, ConstructsArmoryModifiers.PRICKLY.get());

    // Tier 3 Addons
    addArmorTraits(MaterialIds.steel, ConstructsArmoryModifiers.DUCTILE.get());
    addArmorTraits(MaterialIds.bronze, ConstructsArmoryModifiers.IMMACULATE2.get());
    addArmorTraits(MaterialIds.constantan, ConstructsArmoryModifiers.FERVENT.get());
    addArmorTraits(MaterialIds.invar, ConstructsArmoryModifiers.STABLE.get());
    addArmorTraits(MaterialIds.necronium, ConstructsArmoryModifiers.BLIGHTED.get());
    addArmorTraits(MaterialIds.electrum, ConstructsArmoryModifiers.EXPERIENCED.get());
    addArmorTraits(MaterialIds.platedSlimewood, ConstructsArmoryModifiers.OVERWORKED.get());

    // Tier 4
    addArmorTraits(MaterialIds.queensSlime, ConstructsArmoryModifiers.OVERLORD.get());
    addArmorTraits(MaterialIds.ancientHide, ConstructsArmoryModifiers.SALVAGED.get());
    addArmorTraits(MaterialIds.hepatizon, ConstructsArmoryModifiers.ACCELERATION.get());
    addArmorTraits(MaterialIds.manyullyn, ConstructsArmoryModifiers.VENGEFUL.get());
    addArmorTraits(MaterialIds.blazingBone, ConstructsArmoryModifiers.ENKINDLING.get());

    // Tier 5
    addTraits(MaterialIds.enderslimeVine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.ENDERSHIELD.get().getId());
  }

  protected void addArmorTraits(MaterialId materialId, Modifier trait) {
    addTraits(materialId, PlateMaterialStats.ID, trait.getId());
    addTraits(materialId, MailMaterialStats.ID, trait.getId());
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Traits";
  }
}
