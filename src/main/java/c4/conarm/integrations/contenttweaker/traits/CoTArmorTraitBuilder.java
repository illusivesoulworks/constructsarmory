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

package c4.conarm.integrations.contenttweaker.traits;

import c4.conarm.integrations.contenttweaker.ArmorFunctions;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.CoTRecipeMatch;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.Functions;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.contenttweaker.conarm.ArmorTraitBuilder")
@ZenRegister
public class CoTArmorTraitBuilder {

    @ZenProperty
    public String identifier;
    @ZenProperty
    public int color = 16777215;
    @ZenProperty
    public int maxLevel = 0;
    @ZenProperty
    public int countPerLevel = 0;
    @ZenProperty
    public boolean hidden = false;
    @ZenProperty
    public Functions.AfterBlockBreak afterBlockBreak = null;
    @ZenProperty
    public Functions.BeforeBlockBreak beforeBlockBreak = null;
    @ZenProperty
    public Functions.BlockHarvestDrops onBlockHarvestDrops = null;
    @ZenProperty
    public Functions.Damage calcDamage = null;
    @ZenProperty
    public Functions.IsCriticalHit calcCrit = null;
    @ZenProperty
    public Functions.MiningSpeed getMiningSpeed = null;
    @ZenProperty
    public Functions.OnHit onHit = null;
    @ZenProperty
    public Functions.OnUpdate onUpdate = null;
    @ZenProperty
    public Functions.AfterHit afterHit = null;
    @ZenProperty
    public Functions.KnockBack calcKnockBack = null;
    @ZenProperty
    public Functions.OnBlock onBlock = null;
    @ZenProperty
    public Functions.OnToolDamage onToolDamage = null;
    @ZenProperty
    public Functions.OnToolHeal calcToolHeal = null;
    @ZenProperty
    public Functions.OnToolRepair onToolRepair = null;
    @ZenProperty
    public Functions.OnPlayerHurt onPlayerHurt = null;
    @ZenProperty
    public ArmorFunctions.OnArmorTick onArmorTick = null;
    @ZenProperty
    public ArmorFunctions.GetModifications getModifications = null;
    @ZenProperty
    public ArmorFunctions.OnItemPickup onItemPickup = null;
    @ZenProperty
    public ArmorFunctions.OnHurt onHurt = null;
    @ZenProperty
    public ArmorFunctions.OnDamaged onDamaged = null;
    @ZenProperty
    public ArmorFunctions.OnKnockback onKnockback = null;
    @ZenProperty
    public ArmorFunctions.OnFalling onFalling = null;
    @ZenProperty
    public ArmorFunctions.OnJumping onJumping = null;
    @ZenProperty
    public ArmorFunctions.OnAbility onAbility = null;
    @ZenProperty
    public ArmorFunctions.OnArmorChange onArmorChange = null;
    @ZenProperty
    public ArmorFunctions.OnArmorDamaged onArmorDamaged = null;
    @ZenProperty
    public ArmorFunctions.OnArmorHealed onArmorHealed = null;
    @ZenProperty
    public ArmorFunctions.GetAbilityLevel getAbilityLevel = null;
    @ZenProperty
    public Functions.CanApplyTogetherTrait canApplyTogetherTrait = null;
    @ZenProperty
    public Functions.CanApplyTogetherEnchantment canApplyTogetherEnchantment = null;
    @ZenProperty
    public Functions.ExtraInfo extraInfo = null;
    @ZenProperty
    public String localizedName = null;
    @ZenProperty
    public String localizedDescription = null;

    private List<CoTRecipeMatch> recipeMatches = new ArrayList<>();

    public CoTArmorTraitBuilder(String identifier) {
        this.identifier = identifier;
    }

    @ZenMethod
    public static CoTArmorTraitBuilder create(String identifier) {
        return new CoTArmorTraitBuilder(identifier);
    }

    @ZenMethod
    public void addItem(IIngredient ingredient, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 1) int amountMatched) {
        recipeMatches.add(new CoTRecipeMatch(ingredient, amountMatched, amountNeeded));
    }

    @ZenMethod
    public void removeItem(IItemStack itemStack) {
        recipeMatches.removeIf(coTRecipeMatch -> coTRecipeMatch.matches(itemStack));
    }

    @ZenMethod
    public TConTraitRepresentation register() {
        CoTArmorTrait trait = new CoTArmorTrait(identifier, color, maxLevel, countPerLevel);
        trait.afterBlockBreak = this.afterBlockBreak;
        trait.beforeBlockBreak = this.beforeBlockBreak;
        trait.onBlockHarvestDrops = this.onBlockHarvestDrops;
        trait.calcDamage = this.calcDamage;
        trait.calcCrit = this.calcCrit;
        trait.getMiningSpeed = this.getMiningSpeed;
        trait.onHit = this.onHit;
        trait.onUpdate = this.onUpdate;
        trait.afterHit = this.afterHit;
        trait.calcKnockBack = this.calcKnockBack;
        trait.onBlock = this.onBlock;
        trait.onToolDamage = this.onToolDamage;
        trait.calcToolHeal = this.calcToolHeal;
        trait.onToolRepair = this.onToolRepair;
        trait.onPlayerHurt = this.onPlayerHurt;
        trait.onArmorTick = this.onArmorTick;
        trait.getModifications = this.getModifications;
        trait.onItemPickup = this.onItemPickup;
        trait.onHurt = this.onHurt;
        trait.onDamaged = this.onDamaged;
        trait.onKnockback = this.onKnockback;
        trait.onFalling = this.onFalling;
        trait.onJumping = this.onJumping;
        trait.onAbility = this.onAbility;
        trait.onArmorChange = this.onArmorChange;
        trait.onArmorDamaged = this.onArmorDamaged;
        trait.onArmorHealed = this.onArmorHealed;
        trait.getAbilityLevel = this.getAbilityLevel;
        trait.hidden = this.hidden;
        trait.canApplyTogetherTrait = this.canApplyTogetherTrait;
        trait.canApplyTogetherEnchantment = this.canApplyTogetherEnchantment;
        trait.extraInfo = this.extraInfo;
        trait.localizedName = this.localizedName;
        trait.localizedDescription = this.localizedDescription;

        for (CoTRecipeMatch recipeMatch : recipeMatches) {
            trait.addItem(recipeMatch);
        }

        TinkerRegistry.addTrait(trait);

        return new TConTraitRepresentation(trait);
    }
}
