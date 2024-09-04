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

package com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter;

import javax.annotation.Nonnull;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class BlightedModifier extends CounterattackModifier {

  private static MobEffectInstance makeDecayEffect(int level) {
    return new MobEffectInstance(MobEffects.WITHER, 20 * (5 + (RANDOM.nextInt(level * 3))), level - 1);
  }

  @Override
  protected int counter(@Nonnull IToolStackView tool, int level, LivingEntity attacker,
                        @Nonnull EquipmentContext context, @Nonnull EquipmentSlot slotType,
                        DamageSource source, float amount) {

    if (RANDOM.nextFloat() < 0.15f * level) {

      if (RANDOM.nextInt(3) == 0) {
        context.getEntity().addEffect(makeDecayEffect(level));
      }
      attacker.addEffect(makeDecayEffect(level));
      return 1;
    }
    return 0;
  }
}
