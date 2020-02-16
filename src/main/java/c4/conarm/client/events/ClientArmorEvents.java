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

package c4.conarm.client.events;

import c4.conarm.client.gui.PreviewPlayer;
import c4.conarm.common.network.AccessoryTogglePacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import slimeknights.tconstruct.common.TinkerNetwork;

public class ClientArmorEvents {

    public static KeyBinding toggleHelmet;
    public static KeyBinding toggleChestplate;
    public static KeyBinding toggleLeggings;
//    public static KeyBinding toggleBoots;

    public static void init() {
        toggleHelmet = registerKeybinding(new KeyBinding("key.conarm.toggle_helm.desc", Keyboard.KEY_G, "key.conarm.category"));
        toggleChestplate = registerKeybinding(new KeyBinding("key.conarm.toggle_chest.desc", Keyboard.KEY_H, "key.conarm.category"));
        toggleLeggings = registerKeybinding(new KeyBinding("key.conarm.toggle_leg.desc", Keyboard.KEY_J, "key.conarm.category"));
//        toggleBoots = registerKeybinding(new KeyBinding("key.conarm.toggle_boots.desc", Keyboard.KEY_K, "key.conarm.category"));
    }

    private static KeyBinding registerKeybinding(KeyBinding key) {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }

    @SubscribeEvent
    public void onKeyInput(TickEvent.ClientTickEvent evt) {

        if (evt.phase != TickEvent.Phase.END) { return; }

        if (toggleHelmet.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.HEAD.getIndex()));
        } else if (toggleChestplate.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.CHEST.getIndex()));
        } else if (toggleLeggings.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.LEGS.getIndex()));
        }
//        else if (toggleBoots.isPressed()) {
//            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.FEET.getIndex()));
//        }
    }

    //Cancel rendering the name of the fake player in the armor preview
    @SubscribeEvent
    public void onArmorPreview(RenderLivingEvent.Specials.Pre evt) {
        if (evt.getEntity() instanceof PreviewPlayer) {
            evt.setCanceled(true);
        }
    }
}
