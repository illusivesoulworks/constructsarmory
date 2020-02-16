/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TraitAmbitious extends AbstractArmorTrait {

    public TraitAmbitious() {
        super("ambitious", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingXPDrop(LivingExperienceDropEvent evt) {
        int xpToDrop = evt.getDroppedExperience();

        if (xpToDrop > 0) {
            int addXP = getXpToAdd(evt.getAttackingPlayer());

            if (addXP > 0) {
                evt.setDroppedExperience(addXP + xpToDrop);
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent evt) {
        int xpToDrop = evt.getExpToDrop();

        if (xpToDrop > 0) {
            int addXP = getXpToAdd(evt.getPlayer());

            if (addXP > 0) {
                evt.setExpToDrop(xpToDrop + addXP);
            }
        }
    }

    private static int getXpToAdd(EntityPlayer player) {
        int level = (int) ArmorHelper.getArmorAbilityLevel(player, ArmorTraits.ambitious.identifier);
        return level > 0 ? random.nextInt(level * 2 + 1) + 2 : 0;
    }
}
