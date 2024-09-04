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

package com.illusivesoulworks.constructsarmory.common.modifier.trait.general;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import org.apache.commons.lang3.reflect.FieldUtils;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;

import java.lang.reflect.Field;

public class ShieldingModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> SHIELDING =
      ConstructsArmoryMod.createKey("shielding");

  public ShieldingModifier() {
    super(SHIELDING);
    MinecraftForge.EVENT_BUS.addListener(ShieldingModifier::onPotionStart);
  }

  private static void onPotionStart(final PotionEvent.PotionAddedEvent evt) {
    MobEffectInstance newEffect = evt.getPotionEffect();

    if (!newEffect.getCurativeItems().isEmpty()) {
      LivingEntity living = evt.getEntityLiving();
      living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
        int levels = data.get(SHIELDING, 0);

        if (levels > 0) {
          float change = levels * 0.05f;

          if (!newEffect.getEffect().isBeneficial()) {
            change *= -1;
          }
          try {
            Field f_19503_ = FieldUtils.getDeclaredField(MobEffectInstance.class, "f_19503_", true);
            f_19503_.setAccessible(true);
            f_19503_.set(newEffect, Math.max(0, (int) (newEffect.getDuration() * (1 + change))));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }
      });
    }
  }
}
