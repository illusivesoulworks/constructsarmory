package c4.conarm.lib.traits;

import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import javax.annotation.Nonnull;

public class AbstractArmorTrait extends AbstractTrait implements IArmorTrait {

    public AbstractArmorTrait(String identifier, TextFormatting color) {
        super(identifier, color);
    }

    public AbstractArmorTrait(String identifier, int color) {
        super(identifier, color);
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
    public void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt) {
        //NO-OP
    }

    @Override
    public void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt) {
        //NO-OP
    }

    @Override
    public void onAbilityTick(ArmorAbilityHandler.IArmorAbilities abilities, World world, EntityPlayer player) {
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
}
