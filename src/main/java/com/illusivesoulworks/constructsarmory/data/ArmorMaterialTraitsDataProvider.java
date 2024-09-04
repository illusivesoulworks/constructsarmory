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

import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryModifiers;
import com.illusivesoulworks.constructsarmory.common.stat.impl.MailMaterialStats;
import com.illusivesoulworks.constructsarmory.common.stat.impl.PlateMaterialStats;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import javax.annotation.Nonnull;

public class ArmorMaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

  public ArmorMaterialTraitsDataProvider(DataGenerator gen,
                                         AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialTraits() {
    // Tier 1
    addArmorTraits(MaterialIds.rock, ConstructsArmoryModifiers.PETROUS);
    addArmorTraits(MaterialIds.leather, ConstructsArmoryModifiers.WOVEN);
    addArmorTraits(MaterialIds.bone, ConstructsArmoryModifiers.SPLINTERED);
    addTraits(MaterialIds.vine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.SOLAR_POWERED);

    // Tier 2
    addArmorTraits(MaterialIds.iron, ConstructsArmoryModifiers.REINFORCED);
    addArmorTraits(MaterialIds.slimewood, ConstructsArmoryModifiers.OVERGROWTH);
    addArmorTraits(MaterialIds.copper, ConstructsArmoryModifiers.DELVING);
    addArmorTraits(MaterialIds.searedStone, ConstructsArmoryModifiers.IGNEOUS);
    addArmorTraits(MaterialIds.necroticBone, ConstructsArmoryModifiers.MALIGNANT);
    addArmorTraits(MaterialIds.bloodbone, ConstructsArmoryModifiers.BLOODLETTING);
    addTraits(MaterialIds.skyslimeVine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.AERIAL);

    // Tier 2 Addons
    addArmorTraits(MaterialIds.osmium, ConstructsArmoryModifiers.DENSE);
    addArmorTraits(MaterialIds.tungsten, ConstructsArmoryModifiers.WEIGHTY);
    addArmorTraits(MaterialIds.platinum, ConstructsArmoryModifiers.RADIANT);
    addArmorTraits(MaterialIds.silver, ConstructsArmoryModifiers.HALLOWED);
    addArmorTraits(MaterialIds.lead, ConstructsArmoryModifiers.SHIELDING);
    addArmorTraits(MaterialIds.whitestone, ConstructsArmoryModifiers.STONEGUARD);

    // Tier 3
    addArmorTraits(MaterialIds.slimesteel, ConstructsArmoryModifiers.OVERCAST);
    addArmorTraits(MaterialIds.bronze, ConstructsArmoryModifiers.IMMACULATE);
    addArmorTraits(MaterialIds.cobalt, ConstructsArmoryModifiers.NIMBLE);
    addArmorTraits(MaterialIds.pigIron, ConstructsArmoryModifiers.SAVORY);
    addArmorTraits(MaterialIds.nahuatl, ConstructsArmoryModifiers.PRICKLY);

    // Tier 3 Addons
    addArmorTraits(MaterialIds.steel, ConstructsArmoryModifiers.DUCTILE);
    addArmorTraits(MaterialIds.bronze, ConstructsArmoryModifiers.IMMACULATE2);
    addArmorTraits(MaterialIds.constantan, ConstructsArmoryModifiers.FERVENT);
    addArmorTraits(MaterialIds.invar, ConstructsArmoryModifiers.STABLE);
    addArmorTraits(MaterialIds.necronium, ConstructsArmoryModifiers.BLIGHTED);
    addArmorTraits(MaterialIds.electrum, ConstructsArmoryModifiers.EXPERIENCED);
    addArmorTraits(MaterialIds.platedSlimewood, ConstructsArmoryModifiers.OVERWORKED);

    // Tier 4
    addArmorTraits(MaterialIds.queensSlime, ConstructsArmoryModifiers.OVERLORD);
    addArmorTraits(MaterialIds.ancientHide, ConstructsArmoryModifiers.SALVAGED);
    addArmorTraits(MaterialIds.hepatizon, ConstructsArmoryModifiers.ACCELERATION);
    addArmorTraits(MaterialIds.manyullyn, ConstructsArmoryModifiers.VENGEFUL);
    addArmorTraits(MaterialIds.blazingBone, ConstructsArmoryModifiers.ENKINDLING);

    // Tier 5
    addTraits(MaterialIds.enderslimeVine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.ENDERSHIELD);
  }

  protected void addArmorTraits(MaterialId materialId, StaticModifier<? extends Modifier> trait) {
    addTraits(materialId, PlateMaterialStats.ID, trait);
    addTraits(materialId, MailMaterialStats.ID, trait);
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Traits";
  }
}
