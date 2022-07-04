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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public abstract class CounterattackModifier extends Modifier {

  public CounterattackModifier(int color) {
    super(color);
  }

  @Override
  public void onAttacked(@Nonnull IModifierToolStack tool, int level,
                         @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                         DamageSource source, float amount, boolean isDirectDamage) {
    Entity attacker = source.getTrueSource();

    if (attacker instanceof LivingEntity && attacker.isAlive() && isDirectDamage) {
      int durabilityDamage =
          counter(tool, level, (LivingEntity) attacker, context, slotType, source, amount);

      if (durabilityDamage > 0) {
        ToolDamageUtil.damageAnimated(tool, durabilityDamage, context.getEntity(), slotType);
      }
    }
  }

  protected abstract int counter(@Nonnull IModifierToolStack tool, int level,
                                 LivingEntity attacker, @Nonnull EquipmentContext context,
                                 @Nonnull EquipmentSlotType slotType, DamageSource source,
                                 float amount);
}
