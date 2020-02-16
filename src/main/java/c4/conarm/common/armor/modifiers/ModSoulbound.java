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

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ListIterator;

//This is just a copy of the soulbound modifier from Tinkers' Construct with a different identifier
public class ModSoulbound extends ArmorModifier {

    public ModSoulbound() {
        super("soulbound", 0xf5fbac);

        addAspects(new ModifierAspect.DataAspect(this), new ModifierAspect.SingleAspect(this));

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        // nothing to do :(
    }

    // We copy the soulbound items into the players corpse in here
    // HIGH priority so we do it before other possibly death-inventory-modifying mods
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDropsEvent event) {
        if(event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer || event.isCanceled()) {
            return;
        }
        if(event.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }

        ListIterator<EntityItem> iter = event.getDrops().listIterator();
        while(iter.hasNext()) {
            EntityItem ei = iter.next();
            ItemStack stack = ei.getItem();
            // find soulbound items
            if(TinkerUtil.hasModifier(stack.getTagCompound(), this.identifier)) {
                // copy the items back into the dead players inventory
                event.getEntityPlayer().inventory.addItemStackToInventory(stack);
                iter.remove();
            }
        }
    }

    // On respawn we copy the items out of the players corpse, into the new player
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerClone(PlayerEvent.Clone evt) {
        if(!evt.isWasDeath() || evt.isCanceled()) {
            return;
        }
        if(evt.getOriginal() == null || evt.getEntityPlayer() == null || evt.getEntityPlayer() instanceof FakePlayer) {
            return;
        }
        if(evt.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }

        for(int i = 0; i < evt.getOriginal().inventory.mainInventory.size(); i++) {
            ItemStack stack = evt.getOriginal().inventory.mainInventory.get(i);
            if(TinkerUtil.hasModifier(stack.getTagCompound(), this.identifier)) {
                evt.getEntityPlayer().inventory.addItemStackToInventory(stack);
                evt.getOriginal().inventory.mainInventory.set(i, ItemStack.EMPTY);
            }
        }
    }
}
