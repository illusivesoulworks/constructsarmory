package c4.conarm.armor.modifiers;

import c4.conarm.lib.modifiers.AccessoryModifier;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ModTravelGoggles extends AccessoryModifier {

    private static final String TAG_GOGGLES = "goggles";

    public ModTravelGoggles() {
        super("travel_goggles");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onFOVUpdate(EntityViewRenderEvent.FOVModifier evt) {
        if (evt.getEntity() instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) evt.getEntity()).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
            GogglesData data = modtag.getTagData(GogglesData.class);
            if (data.goggles) {
                evt.setFOV(evt.getFOV() * 0.1F);
            }
        }
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        toggleGoggles(armor);
    }

    private void toggleGoggles(ItemStack stack) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        data.goggles = !data.goggles;
        modtag.save();
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && super.canApplyCustom(stack);
    }

    public static class GogglesData extends ModifierNBT {

        boolean goggles = false;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            goggles = tag.getBoolean(TAG_GOGGLES);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setBoolean(TAG_GOGGLES, goggles);
        }
    }
}
