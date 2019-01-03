/*
 * Copyright (c) 2018-2019 <C4>
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
