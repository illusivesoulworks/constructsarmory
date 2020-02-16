/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

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
        if (source.getImmediateSource() instanceof EntityLivingBase && random.nextFloat() < CHANCE) {
            damageEntityBySpines(armor, player, (EntityLivingBase) source.getImmediateSource());
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
        if (attackEntitySecondary(damageSource, attackDamage, target, true, false, false)) {
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_CACTUS, target, 1);
            armorDamage = 3;
        }
        ArmorHelper.damageArmor(armor, damageSource, armorDamage, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        target.hurtResistantTime = oldHurtResistantTime;
    }
}
