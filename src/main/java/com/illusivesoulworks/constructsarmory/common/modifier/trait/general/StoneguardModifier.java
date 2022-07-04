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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.DurabilityShieldModifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.modifiers.traits.general.StoneshieldModifier;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryModifiers;

/**
 * Modified copy of {@link StoneshieldModifier} from Tinkers' Construct
 * MIT License (c) SlimeKnights
 */
public class StoneguardModifier extends DurabilityShieldModifier {

  public StoneguardModifier() {
    super(0xe0e9ec);
    MinecraftForge.EVENT_BUS.addListener(StoneguardModifier::onItemPickup);
  }

  @Override
  protected int getShieldCapacity(IModifierToolStack tool, int level) {
    return (int) (level * 100 * tool.getModifier(ToolStats.DURABILITY));
  }

  @Override
  public int getPriority() {
    return 175;
  }

  private static void onItemPickup(final EntityItemPickupEvent evt) {
    PlayerEntity player = evt.getPlayer();

    if (player.isSpectator()) {
      return;
    }
    EquipmentContext context = new EquipmentContext(player);

    if (!context.hasModifiableArmor()) {
      return;
    }
    ItemStack stack = evt.getItem().getItem();

    if (!TinkerTags.Items.STONESHIELDS.contains(stack.getItem())) {
      return;
    }

    if (!player.world.isRemote() && player.isAlive()) {

      for (EquipmentSlotType slotType : ModifiableArmorMaterial.ARMOR_SLOTS) {
        IModifierToolStack armor = context.getToolInSlot(slotType);

        if (armor != null && !armor.isBroken()) {

          for (ModifierEntry entry : armor.getModifierList()) {

            if (entry.getModifier() == ConstructsArmoryModifiers.STONEGUARD.get()) {
              int addedShield = 0;
              int level = entry.getLevel();
              float chance = level * 0.20f;

              if (chance >= 1.0f) {
                addedShield += stack.getCount();
                stack.shrink(stack.getCount());
              } else {
                int reduced = 0;

                for (int i = 0; i < stack.getCount(); i++) {

                  if (RANDOM.nextFloat() < chance) {
                    reduced++;
                  }
                }
                stack.shrink(reduced);
                addedShield += reduced;
              }

              if (addedShield > 0) {
                ((StoneguardModifier) entry.getModifier()).addShield(armor, level, addedShield * 3);
              }

              if (stack.getCount() == 0) {
                return;
              }
            }
          }
        }
      }
    }
  }

  @Nullable
  @Override
  public Boolean showDurabilityBar(@Nonnull IModifierToolStack tool, int level) {
    return getShield(tool) > 0 ? true : null;
  }

  @Override
  public int getDurabilityRGB(@Nonnull IModifierToolStack tool, int level) {

    if (getShield(tool) > 0) {
      return 0x7f7f7f;
    }
    return -1;
  }
}
