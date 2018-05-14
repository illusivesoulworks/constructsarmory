package c4.conarm.client.events;

import c4.conarm.client.gui.PreviewPlayer;
import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.common.armor.modifiers.ModTravelGoggles;
import c4.conarm.common.network.AccessoryTogglePacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ClientArmorEvents {

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
    public void onKeyInput(TickEvent.ClientTickEvent evt) {

        if (evt.phase != TickEvent.Phase.END) { return; }

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

    //Cancel rendering the name of the fake player in the armor preview
    @SubscribeEvent
    public void onArmorPreview(RenderLivingEvent.Specials.Pre evt) {
        if (evt.getEntity() instanceof PreviewPlayer) {
            evt.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onFOVUpdate(EntityViewRenderEvent.FOVModifier evt) {
        if (evt.getEntity() instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) evt.getEntity()).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, ArmorModifiers.modTravelGoggles.getModifierIdentifier());
            ModTravelGoggles.GogglesData data = modtag.getTagData(ModTravelGoggles.GogglesData.class);
            if (data.goggles) {
                evt.setFOV(evt.getFOV() * 0.1F);
            }
        }
    }
}
