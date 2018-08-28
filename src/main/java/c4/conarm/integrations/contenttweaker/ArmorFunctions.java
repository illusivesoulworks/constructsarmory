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

import c4.conarm.integrations.contenttweaker.traits.ConArmTraitRepresentation;
import c4.conarm.lib.armor.ArmorModifications;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
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
        ArmorModifications handle(ConArmTraitRepresentation thisTrait, IPlayer player, ArmorModifications mods, IItemStack stack, IDamageSource damageSource, double damage, int slot);
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
