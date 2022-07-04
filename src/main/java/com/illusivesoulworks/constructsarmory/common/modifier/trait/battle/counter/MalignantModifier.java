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
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryEffects;

public class MalignantModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> MALIGNANT =
      ConstructsArmoryMod.createKey("malignant");

  public MalignantModifier() {
    super(0x4d4d4d, MALIGNANT);
    MinecraftForge.EVENT_BUS.addListener(MalignantModifier::onHurt);
  }

  private static void onHurt(final LivingHurtEvent evt) {
    Entity entity = evt.getSource().getTrueSource();

    if (entity instanceof LivingEntity) {
      LivingEntity living = (LivingEntity) entity;
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
  public void onAttacked(@Nonnull IModifierToolStack tool, int level,
                         @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                         DamageSource source, float amount, boolean isDirectDamage) {
    Entity attacker = source.getTrueSource();

    if (attacker instanceof LivingEntity && attacker.isAlive() && isDirectDamage &&
        RANDOM.nextFloat() < 0.15f * level) {
      EffectInstance effect = context.getEntity().getActivePotionEffect(
          ConstructsArmoryEffects.MALIGNANT.get());

      if (effect != null) {
        int effectLevel = effect.getAmplifier() + 1;
        float percent = effectLevel / 25f;
        attacker.attackEntityFrom(DamageSource.causeThornsDamage(context.getEntity()),
            2f * level * percent);
        ToolDamageUtil.damageAnimated(tool, 1, context.getEntity(), slotType);
      }
    }
  }
}
