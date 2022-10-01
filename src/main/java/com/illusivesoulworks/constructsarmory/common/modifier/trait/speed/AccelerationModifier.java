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

import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryEffects;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;
import com.illusivesoulworks.constructsarmory.common.modifier.IArmorUpdateModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class AccelerationModifier extends TotalArmorLevelModifier implements IArmorUpdateModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> ACCELERATION =
      ConstructsArmoryMod.createKey("acceleration");

  public AccelerationModifier() {
    super(ACCELERATION);
    MinecraftForge.EVENT_BUS.addListener(AccelerationModifier::onUpdateApply);
  }

  private static void onUpdateApply(final LivingEvent.LivingUpdateEvent evt) {
    LivingEntity living = evt.getEntityLiving();

    if (living.isSpectator()) {
      return;
    }
    EquipmentContext context = new EquipmentContext(living);

    if (!context.hasModifiableArmor()) {
      return;
    }

    if (!living.level.isClientSide && living.isAlive() && living.tickCount % 20 == 0 &&
        living.isSprinting()) {
      AttributeInstance attributeInstance =
          living.getAttribute(Attributes.MOVEMENT_SPEED);

      if (attributeInstance != null) {
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(holder -> {
          int levels = holder.get(ACCELERATION, 0);

          if (levels > 0) {
            int effectLevel =
                Math.min(31, ConstructsArmoryEffects.ACCELERATION.get().getLevel(living) + 1);
            ConstructsArmoryEffects.ACCELERATION.get().apply(living, 30, effectLevel, true);
          }
        });
      }
    }
  }

  @Override
  public void addInformation(@Nonnull IToolStackView armor, int level,
                             @Nullable Player player, @Nonnull List<Component> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      float boost;

      if (player != null && key == TooltipKey.SHIFT) {
        int effectLevel =
            Math.min(31, ConstructsArmoryEffects.ACCELERATION.get().getLevel(player) + 1);
        boost = level * effectLevel / 400f;
      } else {
        boost = 0.0f;
      }

      if (boost > 0) {
        EquipmentUtil.addSpeedTooltip(this, armor, boost, tooltip);
      }
    }
  }

  @Override
  public void onUnequip(@Nonnull IToolStackView tool, int level,
                        EquipmentChangeContext context) {
    LivingEntity livingEntity = context.getEntity();
    IToolStackView newTool = context.getReplacementTool();

    if (newTool == null || newTool.isBroken() || newTool.getModifierLevel(this) != level) {
      AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

      if (attribute != null) {
        attribute.removeModifier(EquipmentUtil.getUuid(getId(), context.getChangedSlot()));
      }
    }
  }

  @Nullable
  @Override
  public <T> T getModule(@Nonnull Class<T> type) {
    return tryModuleMatch(type, IArmorUpdateModifier.class, this);
  }

  @Override
  public void onUpdate(IToolStackView armor, EquipmentSlot slotType, int level,
                       LivingEntity living) {

    if (living.level.isClientSide()) {
      return;
    }
    AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);

    if (attribute == null) {
      return;
    }
    UUID uuid = EquipmentUtil.getUuid(getId(), slotType);
    attribute.removeModifier(uuid);

    if (!armor.isBroken()) {
      applyBoost(armor, slotType, attribute, uuid, level, living);
    }
  }

  protected void applyBoost(IToolStackView armor, EquipmentSlot slotType,
                            AttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {

    if (living.isSprinting()) {
      int effectLevel =
          Math.min(31, ConstructsArmoryEffects.ACCELERATION.get().getLevel(living) + 1);
      float boost = level * effectLevel / 400f;

      if (boost > 0) {
        attribute.addTransientModifier(
            new AttributeModifier(uuid, "constructsarmory.modifier.acceleration", boost,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
      }
    }
  }
}
