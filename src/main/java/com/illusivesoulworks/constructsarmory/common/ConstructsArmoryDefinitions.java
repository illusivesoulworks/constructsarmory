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

package com.illusivesoulworks.constructsarmory.common;

import java.util.EnumMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.Util;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.tools.definition.IToolStatProvider;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.stat.ArmorStatProvider;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstructsArmoryDefinitions {

  public static final Map<ArmorSlotType, IToolStatProvider> ARMOR_STAT_PROVIDERS =
      Util.make(new EnumMap<>(ArmorSlotType.class), map -> {
        for (ArmorSlotType type : ArmorSlotType.values()) {
          map.put(type, new ArmorStatProvider(type));
        }
      });

  public static final ModifiableArmorMaterial MATERIAL_ARMOR =
      ModifiableArmorMaterial.builder(ConstructsArmoryMod.getResource("material_armor"))
          .setStatsProvider(ArmorSlotType.HELMET, ARMOR_STAT_PROVIDERS.get(ArmorSlotType.HELMET))
          .setStatsProvider(ArmorSlotType.CHESTPLATE,
              ARMOR_STAT_PROVIDERS.get(ArmorSlotType.CHESTPLATE))
          .setStatsProvider(ArmorSlotType.LEGGINGS,
              ARMOR_STAT_PROVIDERS.get(ArmorSlotType.LEGGINGS))
          .setStatsProvider(ArmorSlotType.BOOTS, ARMOR_STAT_PROVIDERS.get(ArmorSlotType.BOOTS))
          .setSoundEvent(Sounds.EQUIP_PLATE.getSound())
          .build();
}
