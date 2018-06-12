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

package c4.conarm.lib.traits;

import c4.conarm.lib.armor.ArmorModifications;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class AbstractArmorTrait extends AbstractTrait implements IArmorTrait, IArmorAbility {

    public AbstractArmorTrait(String identifier, TextFormatting color) {
        super(identifier + "_armor", color);
    }

    public AbstractArmorTrait(String identifier, int color) {
        super(identifier + "_armor", color);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        return mods;
    }

    @Override
    public void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt) {
        //NO-OP
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        return newDamage;
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        return newDamage;
    }

    @Override
    public void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt) {
        //NO-OP;
    }

    @Override
    public void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt) {
        //NO-OP
    }

    @Override
    public void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt) {
        //NO-OP
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        //NO-OP
    }

    @Override
    public void onArmorChanged(ItemStack armor, EntityPlayer player, int slot) {
        //NO-OP
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        return newDamage;
    }

    @Override
    public int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot) {
        return newAmount;
    }

    @Override
    public int getAbilityLevel(ModifierNBT data) {
        return data.level;
    }
}
