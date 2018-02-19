package c4.conarm.armor.modifiers;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;

public class ModSilkstep extends ArmorModifierTrait {

    public ModSilkstep() {
        super("silkstep", 0xfbe28b);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (player.fallDistance > 0.0F) {
            player.fallDistance = 0.0F;
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET;
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);

        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        data.armor = Math.max(0f, data.armor - 2f);
        data.toughness = Math.max(0f, data.toughness - 1f);

        TagUtil.setToolTag(rootCompound, data.get());
    }
}
