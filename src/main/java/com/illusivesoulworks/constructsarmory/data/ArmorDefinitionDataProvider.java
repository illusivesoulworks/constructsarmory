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
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryDefinitions;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryItems;
import com.illusivesoulworks.constructsarmory.common.stat.ConstructsArmoryStats;

public class ArmorDefinitionDataProvider extends AbstractToolDefinitionDataProvider {

  public ArmorDefinitionDataProvider(DataGenerator generator) {
    super(generator, ConstructsArmoryMod.MOD_ID);
  }

  @Override
  protected void addToolDefinitions() {
    defineArmor(ConstructsArmoryDefinitions.MATERIAL_ARMOR)
        .part(ArmorSlotType.HELMET, ConstructsArmoryItems.HEAD_PLATE.get(), 1)
        .part(ArmorSlotType.HELMET, ConstructsArmoryItems.MAIL.get(), 1)
        .part(ArmorSlotType.CHESTPLATE, ConstructsArmoryItems.BODY_PLATE.get(), 1)
        .part(ArmorSlotType.CHESTPLATE, ConstructsArmoryItems.MAIL.get(), 1)
        .part(ArmorSlotType.LEGGINGS, ConstructsArmoryItems.LEGS_PLATE.get(), 1)
        .part(ArmorSlotType.LEGGINGS, ConstructsArmoryItems.MAIL.get(), 1)
        .part(ArmorSlotType.BOOTS, ConstructsArmoryItems.FEET_PLATE.get(), 1)
        .part(ArmorSlotType.BOOTS, ConstructsArmoryItems.MAIL.get(), 1)
        .stat(ToolStats.ARMOR, 0)
        .stat(ToolStats.ARMOR_TOUGHNESS, 0)
        .stat(ToolStats.KNOCKBACK_RESISTANCE, 0)
        .stat(ConstructsArmoryStats.MOVEMENT_SPEED, 0)
        .startingSlots(SlotType.UPGRADE, 1)
        .startingSlots(SlotType.DEFENSE, 2)
        .startingSlots(SlotType.ABILITY, 1);
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Armor Definition Data Generator";
  }
}
