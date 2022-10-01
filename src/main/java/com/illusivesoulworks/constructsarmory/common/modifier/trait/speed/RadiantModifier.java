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

package com.illusivesoulworks.constructsarmory.common.modifier.trait.speed;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.LightLayer;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;

public class RadiantModifier extends AbstractSpeedModifier {

  private static final float BOOST_AT_15 = 0.02f;

  private static float getBoost(int lightLevel, int level) {
    return level * BOOST_AT_15 * (lightLevel / 15f);
  }

  @Override
  public void addInformation(@Nonnull IToolStackView armor, int level,
                             @Nullable Player player, @Nonnull List<Component> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      float boost;

      if (player != null && key == TooltipKey.SHIFT) {
        int i = player.level.getBrightness(LightLayer.BLOCK, player.blockPosition());

        if (player.level.dimensionType().hasSkyLight()) {
          player.level.updateSkyBrightness();
          i = Math.max(i, player.level.getBrightness(LightLayer.BLOCK, player.blockPosition()) -
              player.level.getSkyDarken());
        }
        boost = getBoost(i, level);
      } else {
        boost = BOOST_AT_15 * level;
      }

      if (boost > 0) {
        EquipmentUtil.addSpeedTooltip(this, armor, boost, tooltip);
      }
    }
  }

  @Override
  protected void applyBoost(IToolStackView armor, EquipmentSlot slotType,
                            AttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    int i = living.level.getBrightness(LightLayer.BLOCK, living.blockPosition());

    if (living.level.dimensionType().hasSkyLight()) {
      i = Math.max(i, living.level.getBrightness(LightLayer.SKY, living.blockPosition()) -
          living.level.getSkyDarken());
    }
    float boost = getBoost(i, level);

    if (boost > 0) {
      attribute.addTransientModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.radiant", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
