package c4.conarm.armor.modifiers;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModTravelBelt extends ToolModifier {

    public ModTravelBelt() {
        super("travel_belt", 0xcc4400);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS;
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

//        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
//        data.armor = Math.max(0f, data.armor - 2f);
//        data.toughness = Math.max(0f, data.toughness - 1f);
//
//        TagUtil.setToolTag(rootCompound, data.get());
    }

    public static class HotbarData extends ModifierNBT {

        public ItemStackHandler hotbar = new ItemStackHandler(9);

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            hotbar.deserializeNBT((NBTTagCompound) tag.getTag("Hotbar"));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setTag("Hotbar", hotbar.serializeNBT());
        }
    }
}
