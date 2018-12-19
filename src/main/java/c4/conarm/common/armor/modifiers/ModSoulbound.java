/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
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
