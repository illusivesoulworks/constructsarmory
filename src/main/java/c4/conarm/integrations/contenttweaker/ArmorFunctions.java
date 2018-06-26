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

package c4.conarm.integrations.contenttweaker;

import c4.conarm.lib.armor.ArmorModifications;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import stanhebben.zenscript.annotations.ZenClass;

public class ArmorFunctions {

    @ZenClass("mods.conarm.traits.ArmorTick")
    @ZenRegister
    public interface OnArmorTick {
        void handle(TConTraitRepresentation thisTrait, IItemStack armor, IWorld world, IPlayer player);
    }

    @ZenClass("mods.conarm.traits.Modifications")
    @ZenRegister
    public interface GetModifications {
        ArmorModifications handle(TConTraitRepresentation thisTrait, IPlayer player, ArmorModifications mods, IItemStack stack, IDamageSource damageSource, double damage, int slot);
    }

    @ZenClass("mods.conarm.traits.ItemPickup")
    @ZenRegister
    public interface OnItemPickup {
        void handle(TConTraitRepresentation thisTrait, IItemStack stack, IEntityItem entityItem, PlayerPickupItemEvent evt);
    }

    @ZenClass("mods.conarm.traits.Hurt")
    @ZenRegister
    public interface OnHurt {
        float handle(TConTraitRepresentation thisTrait, IItemStack stack, IPlayer player, IDamageSource source, float damage, float newDamage, EntityLivingHurtEvent evt);
    }

    @ZenClass("mods.conarm.traits.Damaged")
    @ZenRegister
    public interface OnDamaged {
        float handle(TConTraitRepresentation thisTrait, IItemStack stack, IPlayer player, IDamageSource source, float damage, float newDamage, LivingDamageEvent evt);
    }

    @ZenClass("mods.conarm.traits.Knockback")
    @ZenRegister
    public interface OnKnockback {
        void handle(TConTraitRepresentation thisTrait, IItemStack stack, IPlayer player, LivingKnockBackEvent evt);
    }

    @ZenClass("mods.conarm.traits.Falling")
    @ZenRegister
    public interface OnFalling {
        void handle(TConTraitRepresentation thisTrait, IItemStack stack, IPlayer player, EntityLivingFallEvent evt);
    }

    @ZenClass("mods.conarm.traits.Jumping")
    @ZenRegister
    public interface OnJumping {
        void handle(TConTraitRepresentation thisTrait, IItemStack stack, IPlayer player, EntityLivingJumpEvent evt);
    }

    @ZenClass("mods.conarm.traits.Ability")
    @ZenRegister
    public interface OnAbility {
        void handle(TConTraitRepresentation thisTrait, int level, IWorld world, IPlayer player);
    }

    @ZenClass("mods.conarm.traits.ArmorChange")
    @ZenRegister
    public interface OnArmorChange {
        void handle(TConTraitRepresentation thisTrait, IItemStack stack, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.ArmorDamaged")
    @ZenRegister
    public interface OnArmorDamaged {
        int handle(TConTraitRepresentation thisTrait, IItemStack stack, IDamageSource source, int damage, int newDamage, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.ArmorHealed")
    @ZenRegister
    public interface OnArmorHealed {
        int handle(TConTraitRepresentation thisTrait, IItemStack stack, IDamageSource source, int damage, int newDamage, IPlayer player, int slot);
    }

    @ZenClass("mods.conarm.traits.AbilityLevel")
    @ZenRegister
    public interface GetAbilityLevel {
        int handle(TConTraitRepresentation thisTrait, ModifierNBT data);
    }
}
