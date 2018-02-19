package c4.conarm.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

public class TraitDuritosRanch extends AbstractArmorTrait {

    public TraitDuritosRanch() {
        super("duritos_ranch", TextFormatting.LIGHT_PURPLE);
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        float rand = random.nextFloat();
        if(rand < 0.1F) {
            //Double durability usage
            return newDamage + damage;
        }
        else if(rand < 0.5F) {
            //No durability usage
            return Math.max(0, newDamage - damage);
        }
        else {
            return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
        }
    }
}
