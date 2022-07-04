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
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryEffects;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;
import com.illusivesoulworks.constructsarmory.common.modifier.IArmorUpdateModifier;

public class AccelerationModifier extends TotalArmorLevelModifier implements IArmorUpdateModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> ACCELERATION =
      ConstructsArmoryMod.createKey("acceleration");

  public AccelerationModifier() {
    super(0x60496b, ACCELERATION);
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

    if (!living.world.isRemote() && living.isAlive() && living.ticksExisted % 20 == 0 &&
        living.isSprinting()) {
      ModifiableAttributeInstance attributeInstance =
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
  public void addInformation(@Nonnull IModifierToolStack armor, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
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
  public void onUnequip(@Nonnull IModifierToolStack tool, int level,
                        EquipmentChangeContext context) {
    LivingEntity livingEntity = context.getEntity();
    IModifierToolStack newTool = context.getReplacementTool();

    if (newTool == null || newTool.isBroken() || newTool.getModifierLevel(this) != level) {
      ModifiableAttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

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
  public void onUpdate(IModifierToolStack armor, EquipmentSlotType slotType, int level,
                       LivingEntity living) {

    if (living.world.isRemote) {
      return;
    }
    ModifiableAttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);

    if (attribute == null) {
      return;
    }
    UUID uuid = EquipmentUtil.getUuid(getId(), slotType);
    attribute.removeModifier(uuid);

    if (!armor.isBroken()) {
      applyBoost(armor, slotType, attribute, uuid, level, living);
    }
  }

  protected void applyBoost(IModifierToolStack armor, EquipmentSlotType slotType,
                            ModifiableAttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {

    if (living.isSprinting()) {
      int effectLevel =
          Math.min(31, ConstructsArmoryEffects.ACCELERATION.get().getLevel(living) + 1);
      float boost = level * effectLevel / 400f;

      if (boost > 0) {
        attribute.applyNonPersistentModifier(
            new AttributeModifier(uuid, "constructsarmory.modifier.acceleration", boost,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
      }
    }
  }
}
