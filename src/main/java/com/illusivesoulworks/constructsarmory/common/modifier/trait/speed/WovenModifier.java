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
import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.modifiers.traits.DamageSpeedTradeModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.EquipmentUtil;

/**
 * Modified copy of {@link DamageSpeedTradeModifier} from Tinkers' Construct
 * MIT License (c) SlimeKnights
 */
public class WovenModifier extends Modifier {

  private final static float MULTIPLIER = 0.005f;
  private final Lazy<String> speedName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".speed";
  });
  private final Lazy<String> armorName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".armor";
  });

  public WovenModifier() {
    super(0xc65c35);
  }

  private float getMultiplier(IModifierToolStack armor, int level) {
    return (float) (Math.sqrt(armor.getDamage() * level / armor.getModifier(ToolStats.DURABILITY)) *
        MULTIPLIER);
  }

  @Override
  public void addAttributes(@Nonnull IModifierToolStack armor, int level,
                            @Nonnull EquipmentSlotType slot,
                            @Nonnull BiConsumer<Attribute, AttributeModifier> consumer) {

    if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR) {
      float boost = getMultiplier(armor, level);

      if (boost != 0) {
        UUID uuid = EquipmentUtil.getUuid(getId(), slot);
        consumer.accept(Attributes.ARMOR, new AttributeModifier(uuid, armorName.get(), -boost * 2,
            AttributeModifier.Operation.MULTIPLY_TOTAL));
        consumer.accept(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(uuid, speedName.get(), boost / 2,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
      }
    }
  }
}
