package c4.conarm.armor;

import c4.conarm.armor.modifiers.ModTravelGoggles;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ClientArmorEvents {

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
