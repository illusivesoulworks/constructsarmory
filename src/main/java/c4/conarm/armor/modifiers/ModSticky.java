package c4.conarm.armor.modifiers;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ModSticky extends ArmorModifierTrait {

    public ModSticky() {
        super("sticky", 0xffffff);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if (evt.getSource().getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase entityIn = (EntityLivingBase) evt.getSource().getImmediateSource();
            if (!entityIn.isPotionActive(MobEffects.SLOWNESS)) {
                if (evt.getEntity() instanceof EntityPlayer) {
                    int level = ArmorHelper.getArmorAbilityLevel((EntityPlayer) evt.getEntity(), this.identifier);
                    entityIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * level, Math.max(1, level / 2)));
                }
            }
        }
        return onDamaged(armor, player, source, damage, newDamage, evt);
    }
}
