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

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;

public class ExperiencedModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> EXPERIENCED =
      ConstructsArmoryMod.createKey("experienced");

  public ExperiencedModifier() {
    super(EXPERIENCED);
    MinecraftForge.EVENT_BUS.addListener(this::onEntityKill);
    MinecraftForge.EVENT_BUS.addListener(this::beforeBlockBreak);
  }

  private static int boost(int original, int level) {
    return (int) (original * (1 + (0.25 * level)));
  }

  private void beforeBlockBreak(final BlockEvent.BreakEvent evt) {
    evt.getPlayer().getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
      int levels = data.get(EXPERIENCED, 0);

      if (levels > 0) {
        evt.setExpToDrop(boost(evt.getExpToDrop(), levels));
      }
    });
  }

  private void onEntityKill(final LivingExperienceDropEvent evt) {
    Player player = evt.getAttackingPlayer();

    if (player != null) {
      player.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
        int levels = data.get(EXPERIENCED, 0);

        if (levels > 0) {
          evt.setDroppedExperience(boost(evt.getDroppedExperience(), levels));
        }
      });
    }
  }
}
