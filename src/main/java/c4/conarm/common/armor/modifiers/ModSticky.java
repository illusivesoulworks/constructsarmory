/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.armor.utils.ArmorHelper;
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
                    int level = (int) ArmorHelper.getArmorAbilityLevel((EntityPlayer) evt.getEntity(), this.identifier);
                    entityIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * level, Math.max(1, level / 2)));
                }
            }
        }
        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }
}
