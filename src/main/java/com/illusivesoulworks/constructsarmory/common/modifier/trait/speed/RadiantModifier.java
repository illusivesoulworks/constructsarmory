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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.LightType;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;

public class RadiantModifier extends AbstractSpeedModifier {

  private static final float BOOST_AT_15 = 0.02f;

  public RadiantModifier() {
    super(0xa3e7fe);
  }

  private static float getBoost(int lightLevel, int level) {
    return level * BOOST_AT_15 * (lightLevel / 15f);
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack armor, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      float boost;

      if (player != null && key == TooltipKey.SHIFT) {
        int i = player.world.getLightFor(LightType.BLOCK, player.getPosition());

        if (player.world.getDimensionType().hasSkyLight()) {
          player.world.calculateInitialSkylight();
          i = Math.max(i, player.world.getLightFor(LightType.SKY, player.getPosition()) -
              player.world.getSkylightSubtracted());
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
  protected void applyBoost(IModifierToolStack armor, EquipmentSlotType slotType,
                            ModifiableAttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    int i = living.world.getLightFor(LightType.BLOCK, living.getPosition());

    if (living.world.getDimensionType().hasSkyLight()) {
      i = Math.max(i, living.world.getLightFor(LightType.SKY, living.getPosition()) -
          living.world.getSkylightSubtracted());
    }
    float boost = getBoost(i, level);

    if (boost > 0) {
      attribute.applyNonPersistentModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.radiant", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
