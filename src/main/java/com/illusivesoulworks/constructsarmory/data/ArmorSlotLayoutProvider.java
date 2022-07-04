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

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.IItemProvider;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryItems;

public class ArmorSlotLayoutProvider extends AbstractStationSlotLayoutProvider {

  private static final int SORT_ARMOR = 16;

  public ArmorSlotLayoutProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected void addLayouts() {
    Map<ArmorSlotType, IItemProvider> plates = new HashMap<>();
    plates.put(ArmorSlotType.HELMET, ConstructsArmoryItems.HEAD_PLATE);
    plates.put(ArmorSlotType.CHESTPLATE, ConstructsArmoryItems.BODY_PLATE);
    plates.put(ArmorSlotType.LEGGINGS, ConstructsArmoryItems.LEGS_PLATE);
    plates.put(ArmorSlotType.BOOTS, ConstructsArmoryItems.FEET_PLATE);
    ConstructsArmoryItems.MATERIAL_ARMOR.forEach((slotType, item) ->
        defineModifiable(item)
            .sortIndex(SORT_ARMOR)
            .addInputItem(plates.get(slotType), 37, 48)
            .addInputItem(ConstructsArmoryItems.MAIL, 19, 30)
            .build());
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Tinker Station Slot Layouts";
  }
}
