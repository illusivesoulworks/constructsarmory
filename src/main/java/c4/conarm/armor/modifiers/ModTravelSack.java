package c4.conarm.armor.modifiers;

import c4.conarm.ConstructsArmory;
import c4.conarm.client.GuiHandler;
import c4.conarm.lib.modifiers.AccessoryModifier;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public class ModTravelSack extends AccessoryModifier {

    public static final int SACK_SIZE = 27;

    private static final String TAG_KNAPSACK = "knapsack";

    public ModTravelSack() {
        super("travel_sack");
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);
        NBTTagCompound oldSack = modifierTag.getCompoundTag(TAG_KNAPSACK);
        if (oldSack.hasNoTags()) {
            modifierTag.setTag(TAG_KNAPSACK, (new ItemStackHandler(SACK_SIZE)).serializeNBT());
        } else {
            modifierTag.setTag(TAG_KNAPSACK, oldSack);
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST && super.canApplyCustom(stack);
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        openKnapsack(player);
    }

    private void openKnapsack(EntityPlayer player) {
        player.openGui(ConstructsArmory.instance, GuiHandler.GUI_KNAPSACK_ID, player.world, 0, 0, 0);
    }

    public static class KnapsackData extends ModifierNBT {

        public ItemStackHandler knapsack = new ItemStackHandler(SACK_SIZE);

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            knapsack.deserializeNBT((NBTTagCompound) tag.getTag(TAG_KNAPSACK));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setTag(TAG_KNAPSACK, knapsack.serializeNBT());
        }
    }
}
