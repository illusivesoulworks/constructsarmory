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

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;

public class FerventModifier extends AbstractSpeedModifier {

  private static final float BASELINE_TEMPERATURE = 0.75f;

  private static float getBonus(LivingEntity player, BlockPos pos, int level) {
    return Math.abs(player.level.getBiome(pos).value().getBaseTemperature() - BASELINE_TEMPERATURE) * level /
        62.5f;
  }

  @Override
  public void addInformation(@Nonnull IToolStackView armor, int level,
                             @Nullable Player player, @Nonnull List<Component> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {
    float bonus;

    if (player != null && key == TooltipKey.SHIFT) {
      bonus = getBonus(player, player.blockPosition(), level);
    } else {
      bonus = level * 0.125f;
    }
    if (bonus >= 0.01f) {
      EquipmentUtil.addSpeedTooltip(this, armor, bonus, tooltip);
    }
  }

  @Override
  protected void applyBoost(IToolStackView armor, EquipmentSlot slotType,
                            AttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    float boost = getBonus(living, living.blockPosition(), level);

    if (boost > 0) {
      attribute.addTransientModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.fervent", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
