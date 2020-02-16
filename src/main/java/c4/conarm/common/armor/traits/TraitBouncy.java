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

import c4.conarm.common.network.ArmorBouncedPacket;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.SlimeBounceHandler;

public class TraitBouncy extends AbstractArmorTrait {

    private static final float BOUNCE_STR = 1.5F;

    public TraitBouncy() {
        super("bouncy", 0xbcffbe);
    }

    @Override
    public void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt) {

        evt.setStrength(evt.getStrength() * BOUNCE_STR);
    }

    //Copy of Tinkers' Slime Boots behavior
    @Override
    public void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt) {

        if(EntityLiving.getSlotForItemStack(armor) != EntityEquipmentSlot.FEET) {
            return;
        }

        boolean isClient = player.getEntityWorld().isRemote;
        if(!player.isSneaking() && evt.getDistance() > 2) {
            evt.setDamageMultiplier(0);
            player.fallDistance = 0;
            if(isClient) {
                double motionY = player.motionY;
                player.motionY *= -0.9;
                player.isAirBorne = true;
                player.onGround = false;
                double f = 0.91d + 0.04d;
                player.motionX /= f;
                player.motionZ /= f;
                TinkerNetwork.sendToServer(new ArmorBouncedPacket(Math.abs(motionY)));
            }
            else {
                evt.setCanceled(true);
            }
            player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1f, 1f);
            SlimeBounceHandler.addBounceHandler(player, player.motionY);
        }
        else if(!isClient && player.isSneaking()) {
            evt.setDamageMultiplier(0.2f);
        }
    }
}
