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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.traits.ITrait;

public interface IArmorTrait extends ITrait {

    //TODO: 1.13+ port - Do these parameters need to be EntityPlayer?

    /**
     * Called when calculating armor protection values for the wearer
     * See {@link net.minecraftforge.common.ISpecialArmor#getProperties(EntityLivingBase, ItemStack, DamageSource, double, int)}
     * @param player The wearer of the armor
     * @param mods The {@link c4.conarm.lib.armor.ArmorModifications} that was passed to the trait, subject to
     *             changes from other traits and modifiers
     * @param armor The ItemStack that has this trait
     * @param source The damage source that was received by the wearer
     * @param damage The amount of damage that was received by the wearer
     * @param slot The slot index of the worn armor piece
     * @return The {@link c4.conarm.lib.armor.ArmorModifications} that will be applied to the armor calculations
     */
    ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot);

    /**
     * Called when the wearer picks up an {@link net.minecraft.entity.item.EntityItem}
     * See {@link net.minecraftforge.event.entity.player.EntityItemPickupEvent evt}
     * @param armor The ItemStack that has this trait
     * @param item The ItemStack that was picked up
     * @param evt The event that was forwarded to the trait
     */
    void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt);

    /**
     * Called when the wearer heals damage
     * See {@link net.minecraftforge.event.entity.living.LivingHealEvent}
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param amount The original healing amount
     * @param newAmount The current healing amount, subject to change from other traits and modifiers
     * @param evt The event that was forwarded to the trait
     * @return The amount that the wearer will heal
     */
    float onHeal(ItemStack armor, EntityPlayer player, float amount, float newAmount, LivingHealEvent evt);

    /**
     * Called when the wearer takes damage, but before damage calculations occur
     * See {@link net.minecraftforge.event.entity.living.LivingHurtEvent}
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param source The damage source that was received
     * @param damage The original damage amount
     * @param newDamage The current damage amount, subject to change from other traits and modifiers
     * @param evt The event that was forwarded to the trait
     * @return The amount of damage that the wearer will take before damage calculations
     */
    float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt);

    /**
     * Called when the wearer takes damage, but after damage calculations occur
     * See {@link net.minecraftforge.event.entity.living.LivingDamageEvent}
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param source The damage source that was received
     * @param damage The original damage amount
     * @param newDamage The current damage amount, subject to change from other traits and modifiers
     * @param evt The event that was forwarded to the trait
     * @return The amount of damage that the wearer will take after damage calculations, but still subject to other
     * traits and modifiers that call this method
     */
    float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt);

    /**
     * Called when the wearer is knocked back
     * See {@link net.minecraftforge.event.entity.living.LivingKnockBackEvent}
     * Note that the event that was forwarded must be manipulated directly, as there are no hooks in this method to
     * interact with it otherwise
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param evt The event that was forwarded to the trait
     */
    void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt);

    /**
     * Called when the wearer is set to fall
     * See {@link net.minecraftforge.event.entity.living.LivingFallEvent}
     * Note that the event that was forwarded must be manipulated directly, as there are no hooks in this method to
     * interact with it otherwise
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param evt The event that was forwarded to the trait
     */
    void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt);

    /**
     * Called when the wearer jumps
     * See {@link net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent}
     * Note that the event that was forwarded must be manipulated directly, as there are no hooks in this method to
     * interact with it otherwise
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param evt The event that was forwarded to the trait
     */
    void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt);

    /**
     * Called every tick to update the armor abilities, independent of each ItemStack instance of the trait or modifier
     * Used for traits and modifiers that need the total level of itself present across all armor pieces in order to
     * do calculations or perform effects
     * @param level The total level of the trait or modifier present across all armor pieces
     * @param world The world of the wearer
     * @param player The wearer of the ability
     */
    void onAbilityTick(int level, World world, EntityPlayer player);

    /**
     * Called when the armor is equipped by the wearer
     * See {@link net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent}
     * Note that durability changes in the same ItemStack will also call this event, as it technically counts as a
     * different piece of armor
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param slot The slot index that the armor is equipped into
     */
    void onArmorEquipped(ItemStack armor, EntityPlayer player, int slot);

    /**
     * Called when the armor is removed by the wearer
     * See {@link net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent}
     * Note that durability changes in the same ItemStack will also call this event, as it technically counts as a
     * different piece of armor
     * @param armor The ItemStack that has this trait
     * @param player The wearer of the armor
     * @param slot The slot index that the armor is removed from
     */
    void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot);

    /**
     * Called when the armor takes durability damage
     * @param armor The ItemStack that has this trait
     * @param source The damage source that was received by the wearer
     * @param damage The original amount of durability damage
     * @param newDamage The current amount of durability damage, subject to change from other traits or modifiers
     * @param player The wearer of the armor
     * @param slot The slot index that the armor is in
     * @return The new amount of durability damage
     */
    int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot);

    /**
     * Called when the armor heals durability
     * This includes repairing the armor normally
     * @param armor The ItemStack that has this trait
     * @param source The damage source that was received by the wearer
     * @param amount The original amount of durability to repair
     * @param newAmount The current amount of durability to repair, subject to change from other traits or modifiers
     * @param player The wearer of the armor
     * @param slot The slot index that the armor is in
     * @return The new amount of durability to repair
     */
    int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot);

    /**
     * Called when rendering the armor model on the wearer
     * @param armor The ItemStack that has this trait
     * @param entityLivingBase The wearer of the armor
     * @return Whether to disable rendering of the armor model
     */
    boolean disableRendering(ItemStack armor, EntityLivingBase entityLivingBase);
}
