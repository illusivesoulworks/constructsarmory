package c4.conarm.common.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraitDramatic extends AbstractArmorTrait {

    private static final float CHANCE = 0.1F;

    public TraitDramatic() {
        super("dramatic", 0xff0000);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if ((player.getHealth() - newDamage) <= 0 && random.nextFloat() <= CHANCE) {
            player.heal(2);
            return 0;
        }
        return newDamage;
    }
}
