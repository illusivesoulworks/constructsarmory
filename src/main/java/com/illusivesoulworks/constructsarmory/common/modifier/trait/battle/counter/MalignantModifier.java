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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryEffects;

public class MalignantModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> MALIGNANT =
      ConstructsArmoryMod.createKey("malignant");

  public MalignantModifier() {
    super(MALIGNANT);
    MinecraftForge.EVENT_BUS.addListener(MalignantModifier::onHurt);
  }

  private static void onHurt(final LivingHurtEvent evt) {
    Entity entity = evt.getSource().getEntity();

    if (entity instanceof LivingEntity living) {
      living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(holder -> {
        int levels = holder.get(MALIGNANT, 0);

        if (levels > 0) {
          int effectLevel = Math.min(25, ConstructsArmoryEffects.MALIGNANT.get().getLevel(living) +
              Math.max(1, (int) evt.getAmount()));
          ConstructsArmoryEffects.MALIGNANT.get().apply(living, 5 * 20, effectLevel, true);
        }
      });
    }
  }

  @Override
  public void onAttacked(@Nonnull IToolStackView tool, int level,
                         @Nonnull EquipmentContext context, @Nonnull EquipmentSlot slotType,
                         DamageSource source, float amount, boolean isDirectDamage) {
    Entity attacker = source.getEntity();

    if (attacker instanceof LivingEntity && attacker.isAlive() && isDirectDamage &&
        RANDOM.nextFloat() < 0.15f * level) {
      MobEffectInstance effect = context.getEntity().getEffect(
          ConstructsArmoryEffects.MALIGNANT.get());

      if (effect != null) {
        int effectLevel = effect.getAmplifier() + 1;
        float percent = effectLevel / 25f;
        attacker.hurt(DamageSource.thorns(context.getEntity()),
            2f * level * percent);
        ToolDamageUtil.damageAnimated(tool, 1, context.getEntity(), slotType);
      }
    }
  }
}
