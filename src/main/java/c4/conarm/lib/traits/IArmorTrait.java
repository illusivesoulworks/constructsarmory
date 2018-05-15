/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.lib.traits;

import c4.conarm.lib.armor.ArmorModifications;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.traits.ITrait;

public interface IArmorTrait extends ITrait {

    ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot);

    void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt);

    float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt);

    float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt);

    void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt);

    void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt);

    void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt);

    void onAbilityTick(int level, World world, EntityPlayer player);

    int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot);

    int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot);
}
