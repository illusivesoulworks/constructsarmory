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

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;
import com.illusivesoulworks.constructsarmory.common.modifier.IArmorUpdateModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public abstract class AbstractSpeedModifier extends Modifier implements IArmorUpdateModifier {

  @Override
  public void onUnequip(IToolStackView tool, int level,
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

  protected abstract void applyBoost(IToolStackView armor, EquipmentSlot slotType,
                                     AttributeInstance attribute, UUID uuid, int level,
                                     LivingEntity living);
}
