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

package c4.conarm.integrations.contenttweaker.utils;

import c4.conarm.integrations.contenttweaker.traits.ConArmTraitRepresentation;
import c4.conarm.lib.armor.ArmorModifications;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.EntityLivingFallEvent;
import crafttweaker.api.event.EntityLivingHurtEvent;
import crafttweaker.api.event.EntityLivingJumpEvent;
import crafttweaker.api.event.LivingKnockBackEvent;
import crafttweaker.api.event.PlayerPickupItemEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import stanhebben.zenscript.annotations.ZenClass;

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
public class ArmorFunctions {

    @ZenClass("mods.conarm.traits.Update")
    @ZenRegister
    public interface OnUpdate {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack tool, IWorld world, IEntity entity, int itemSlot, boolean isSelected);
    }

    @ZenClass("mods.conarm.traits.CanApplyTogetherTrait")
    @ZenRegister
    public interface CanApplyTogetherTrait {
        boolean handle(ConArmTraitRepresentation thisTrait, String otherTrait);
    }

    @ZenClass("mods.conarm.traits.CanApplyTogetherEnchantment")
    @ZenRegister
    public interface CanApplyTogetherEnchantment {
        boolean handle(ConArmTraitRepresentation thisTrait, IEnchantmentDefinition enchantmentDefinition);
    }

    @ZenClass("mods.conarm.traits.ExtraInfo")
    @ZenRegister
    public interface ExtraInfo {
        String[] handle(ConArmTraitRepresentation thisTrait, IItemStack item, IData tool);
    }

    @ZenClass("mods.conarm.traits.ArmorRepair")
    @ZenRegister
    public interface OnArmorRepair {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack tool, int amount);
    }

    @ZenClass("mods.conarm.traits.ArmorTick")
    @ZenRegister
    public interface OnArmorTick {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack armor, IWorld world, IPlayer player);
    }

    @ZenClass("mods.conarm.traits.Modifications")
    @ZenRegister
    public interface GetModifications {
        IArmorModifications handle(ConArmTraitRepresentation thisTrait, IPlayer player, IArmorModifications mods, IItemStack stack, IDamageSource damageSource, double damage, int slot);
    }

    @ZenClass("mods.conarm.traits.ItemPickup")
    @ZenRegister
    public interface OnItemPickup {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IEntityItem entityItem, PlayerPickupItemEvent evt);
    }

    @ZenClass("mods.conarm.traits.Heal")
    @ZenRegister
    public interface OnHeal {
        float handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, float amount, float newAmount, LivingHealEvent evt);
    }

    @ZenClass("mods.conarm.traits.Hurt")
    @ZenRegister
    public interface OnHurt {
        float handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, IDamageSource source, float damage, float newDamage, EntityLivingHurtEvent evt);
    }

    @ZenClass("mods.conarm.traits.Damaged")
    @ZenRegister
    public interface OnDamaged {
        float handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, IDamageSource source, float damage, float newDamage, LivingDamageEvent evt);
    }

    @ZenClass("mods.conarm.traits.Knockback")
    @ZenRegister
    public interface OnKnockback {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, LivingKnockBackEvent evt);
    }

    @ZenClass("mods.conarm.traits.Falling")
    @ZenRegister
    public interface OnFalling {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, EntityLivingFallEvent evt);
    }

    @ZenClass("mods.conarm.traits.Jumping")
    @ZenRegister
    public interface OnJumping {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, EntityLivingJumpEvent evt);
    }

    @ZenClass("mods.conarm.traits.Ability")
    @ZenRegister
    public interface OnAbility {
        void handle(ConArmTraitRepresentation thisTrait, int level, IWorld world, IPlayer player);
    }

    @ZenClass("mods.conarm.traits.ArmorEquipped")
    @ZenRegister
    public interface OnArmorEquip {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.ArmorRemoved")
    @ZenRegister
    public interface OnArmorRemove {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, int slot);
    }

    //DO NOT USE!
    //This is not called anywhere, this is just for binary compatibility
    @Deprecated
    @ZenClass("mods.conarm.traits.ArmorChange")
    @ZenRegister
    public interface OnArmorChange {
        void handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.ArmorDamaged")
    @ZenRegister
    public interface OnArmorDamaged {
        int handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IDamageSource source, int damage, int newDamage, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.ArmorHealed")
    @ZenRegister
    public interface OnArmorHealed {
        int handle(ConArmTraitRepresentation thisTrait, IItemStack stack, IDamageSource source, int damage, int newDamage, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.AbilityLevel")
    @ZenRegister
    public interface GetAbilityLevel {
        int handle(ConArmTraitRepresentation thisTrait, ModifierNBT data);
    }
}
