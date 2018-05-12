package c4.conarm.common.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraitShielding extends AbstractArmorTrait {

    private static final float MODIFIER = 0.1F;

    public TraitShielding() {
        super("shielding", 0xffffff);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (source == DamageSource.WITHER || source == DamageSource.MAGIC) {
            newDamage -= damage * MODIFIER;
        }

        return newDamage;
    }

}
