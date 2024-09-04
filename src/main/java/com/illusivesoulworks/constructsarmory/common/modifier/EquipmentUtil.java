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

package com.illusivesoulworks.constructsarmory.common.modifier;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;

import java.util.*;

public class EquipmentUtil {

  private static final Map<ModifierId, Map<EquipmentSlot, UUID>> UUIDS = new HashMap<>();

  public static UUID getUuid(ModifierId id, EquipmentSlot slotType) {
    return UUIDS.computeIfAbsent(id, (k) -> new EnumMap<>(EquipmentSlot.class))
        .computeIfAbsent(slotType, (k) -> {
          String key = id + slotType.toString();
          return UUID.nameUUIDFromBytes(key.getBytes());
        });
  }

  public static void addResistanceTooltip(Modifier modifier, IToolStackView armor,
                                          float multiplier, List<Component> tooltip) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      tooltip.add(modifier.applyStyle(new TextComponent(
              Util.PERCENT_BOOST_FORMAT.format(multiplier / 25f))
              .append(" ")
              .append(
                  new TranslatableComponent(modifier.getTranslationKey() + ".resistance"))));
    }
  }

  public static void addSpeedTooltip(Modifier modifier, IToolStackView armor, float multiplier,
                                     List<Component> tooltip) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      tooltip.add(modifier.applyStyle(new TextComponent(
          Util.PERCENT_BOOST_FORMAT.format(multiplier))
          .append(" ")
          .append(
              new TranslatableComponent(modifier.getTranslationKey() + ".speed"))));
    }
  }
}
