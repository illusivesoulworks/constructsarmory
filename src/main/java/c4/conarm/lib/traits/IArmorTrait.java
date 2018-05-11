package c4.conarm.lib.traits;

import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import slimeknights.tconstruct.library.traits.ITrait;

import javax.annotation.Nonnull;

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
