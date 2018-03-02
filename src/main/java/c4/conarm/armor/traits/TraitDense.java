package c4.conarm.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitDense extends AbstractArmorTrait {

    public TraitDense() {
        super("dense", 0xffffff);
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        float durability = ToolHelper.getCurrentDurability(armor);
        float maxDurability = ToolHelper.getMaxDurability(armor);

        float chance = 0.75F * (1F - durability / maxDurability);
        chance = (float) Math.pow(chance, 3);

        if(chance > random.nextFloat()) {
            newDamage -= Math.max(damage / 2, 1);
        }

        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }
}
