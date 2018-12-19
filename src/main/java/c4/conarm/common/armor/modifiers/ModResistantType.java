/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
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
