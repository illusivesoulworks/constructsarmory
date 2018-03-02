package c4.conarm.armor.modifiers;

import c4.conarm.lib.modifiers.AccessoryModifier;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ModTravelBelt extends AccessoryModifier {

    private static final String TAG_HOTBAR = "hotbar";

    public ModTravelBelt() {
        super("travel_belt");
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        swapHotbars(armor, player);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);
        modifierTag.setTag(TAG_HOTBAR, ((new ItemStackHandler(9)).serializeNBT()));

    }

    private void swapHotbars(ItemStack stack, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        HotbarData data = modtag.getTagData(HotbarData.class);

        ItemStackHandler belt = data.hotbar;

        for (int i = 0; i < belt.getSlots(); i++) {
            ItemStack heldStack = player.inventory.getStackInSlot(i).copy();
            ItemStack beltStack = belt.getStackInSlot(i).copy();
            player.inventory.setInventorySlotContents(i, beltStack);
            belt.setStackInSlot(i, heldStack);
        }

        modtag.save();
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS && super.canApplyCustom(stack);
    }

    public static class HotbarData extends ModifierNBT {

        ItemStackHandler hotbar = new ItemStackHandler(9);

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            hotbar.deserializeNBT((NBTTagCompound) tag.getTag(TAG_HOTBAR));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setTag(TAG_HOTBAR, hotbar.serializeNBT());
        }
    }
}
