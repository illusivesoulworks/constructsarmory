package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitSpiny extends AbstractArmorTrait {

    private static final float CHANCE = 0.3F;

    public TraitSpiny() {
        super("spiny", TextFormatting.DARK_GREEN);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if (evt.getSource().getImmediateSource() instanceof EntityLivingBase && random.nextFloat() < CHANCE) {
            damageEntityBySpines(armor, player, (EntityLivingBase) evt.getSource().getImmediateSource());
        }
        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    private void damageEntityBySpines(ItemStack armor, EntityPlayer player, EntityLivingBase target) {
        EntityDamageSource damageSource = new EntityDamageSource(DamageSource.CACTUS.damageType, player);
        damageSource.setDamageBypassesArmor();
        damageSource.setDamageIsAbsolute();
        damageSource.setIsThornsDamage();
        int oldHurtResistantTime = target.hurtResistantTime;
        int armorDamage = 1;
        int attackDamage = random.nextInt(4) + 1;
        if (attackEntitySecondary(damageSource, attackDamage, target, true, false)) {
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_CACTUS, target, 1);
            armorDamage = 3;
        }
        ArmorHelper.damageArmor(armor, damageSource, armorDamage, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        target.hurtResistantTime = oldHurtResistantTime;
    }
}
