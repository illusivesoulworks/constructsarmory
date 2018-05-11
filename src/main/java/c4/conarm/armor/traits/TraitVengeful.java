package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraitVengeful extends AbstractArmorTrait {

    private static final float MODIFIER = 0.2F;

    public TraitVengeful() {
        super("vengeful", 0xff0000);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (source.getTrueSource() instanceof EntityLivingBase) {
            if (random.nextFloat() < 0.15F) {
                Potion potion;
                switch(random.nextInt(4)) {
                    case 0:
                        potion = MobEffects.POISON;
                        break;
                    case 1:
                        potion = MobEffects.SLOWNESS;
                        break;
                    case 2:
                        potion = MobEffects.WITHER;
                        break;
                    case 3:
                        potion = MobEffects.WEAKNESS;
                        break;
                    default:
                        potion = MobEffects.POISON;
                        break;
                }
                ((EntityLivingBase) source.getTrueSource()).addPotionEffect(new PotionEffect(potion, 100, 0));
                ArmorHelper.damageArmor(armor, source, 3, player, EntityLiving.getSlotForItemStack(armor).getIndex());
            }
        }
        return super.onHurt(armor, player, source, damage, newDamage, evt);
    }
}
