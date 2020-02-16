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

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.ListIterator;

public class ModResistantType extends ArmorModifierTrait {

    private static final float BASE_REDUCTION = 0.02F;
    private final EnchantmentProtection.Type type;

    public ModResistantType(String identifier, int color, EnchantmentProtection.Type type) {
        super(identifier, color, 8, 0);
        this.type = type;
        ListIterator<ModifierAspect> iter = aspects.listIterator();
        while(iter.hasNext()) {
            if(iter.next() == ModifierAspect.freeModifier) {
                iter.set(new ModifierAspect.FreeFirstModifierAspect(this, 1));
            }
        }
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {

        float reduction = 0;
        NBTTagCompound tag = TinkerUtil.getModifierTag(armor, identifier);
        ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(tag);

        if (type == EnchantmentProtection.Type.EXPLOSION && source.isExplosion()) {
            reduction = BASE_REDUCTION * 2;
        }
        else if (type == EnchantmentProtection.Type.FIRE && source.isFireDamage()) {
            reduction = BASE_REDUCTION * 2;
        }
        else if (type == EnchantmentProtection.Type.PROJECTILE && source.isProjectile()) {
            reduction = BASE_REDUCTION * 2;
        }
        else if (type == EnchantmentProtection.Type.ALL) {
            reduction = BASE_REDUCTION;
        }

        newDamage -= damage * reduction * data.level;

        return newDamage;
    }

    @Override
    public boolean canApplyTogether(IToolMod mod) {

        return this == mod || !(mod instanceof ModResistantType);
    }

}
