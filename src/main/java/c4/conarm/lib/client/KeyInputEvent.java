package c4.conarm.lib.client;

import c4.conarm.armor.common.network.AccessoryTogglePacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import slimeknights.tconstruct.common.TinkerNetwork;

public class KeyInputEvent {

    private static KeyBinding toggleHelmet;
    private static KeyBinding toggleChestplate;
    private static KeyBinding toggleLeggings;
    private static KeyBinding toggleBoots;

    public static void init() {
        toggleHelmet = registerKeybinding(new KeyBinding("key.conarm.toggle_helm.desc", Keyboard.KEY_G, "key.conarm.category"));
        toggleChestplate = registerKeybinding(new KeyBinding("key.conarm.toggle_chest.desc", Keyboard.KEY_H, "key.conarm.category"));
        toggleLeggings = registerKeybinding(new KeyBinding("key.conarm.toggle_leg.desc", Keyboard.KEY_J, "key.conarm.category"));
        toggleBoots = registerKeybinding(new KeyBinding("key.conarm.toggle_boots.desc", Keyboard.KEY_K, "key.conarm.category"));
    }

    private static KeyBinding registerKeybinding(KeyBinding key) {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent evt) {

        if (toggleHelmet.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.HEAD.getIndex()));
        } else if (toggleChestplate.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.CHEST.getIndex()));
        } else if (toggleLeggings.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.LEGS.getIndex()));
        } else if (toggleBoots.isPressed()) {
            TinkerNetwork.sendToServer(new AccessoryTogglePacket(EntityEquipmentSlot.FEET.getIndex()));
        }
    }
}
