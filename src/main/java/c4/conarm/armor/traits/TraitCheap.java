package c4.conarm.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;

public class TraitCheap extends AbstractArmorTrait {

    public TraitCheap() {
        super("cheap_armor", TextFormatting.DARK_GRAY);
    }

    @Override
    public int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot) {
        return newAmount + amount * 5 / 100;
    }
}
