/*
 * Copyright (c) 2018-2019 <C4>
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

import c4.conarm.integrations.contenttweaker.utils.ArmorFunctions;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.CoTRecipeMatch;
import crafttweaker.annotations.ModOnly;
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

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
@ZenClass("mods.contenttweaker.conarm.ArmorTraitBuilder")
@ZenRegister
@ModOnly("contenttweaker")
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
    public ArmorFunctions.OnUpdate onUpdate = null;
    @ZenProperty
    public ArmorFunctions.OnArmorRepair onArmorRepair = null;
    @ZenProperty
    public ArmorFunctions.OnArmorTick onArmorTick = null;
    @ZenProperty
    public ArmorFunctions.GetModifications getModifications = null;
    @ZenProperty
    public ArmorFunctions.OnItemPickup onItemPickup = null;
    @ZenProperty
    public ArmorFunctions.OnHeal onHeal = null;
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
    public ArmorFunctions.OnArmorEquip onArmorEquip = null;
    @ZenProperty
    public ArmorFunctions.OnArmorRemove onArmorRemove = null;
    @ZenProperty
    public ArmorFunctions.OnArmorDamaged onArmorDamaged = null;
    @ZenProperty
    public ArmorFunctions.OnArmorHealed onArmorHealed = null;
    @ZenProperty
    public ArmorFunctions.GetAbilityLevel getAbilityLevel = null;
    @ZenProperty
    public ArmorFunctions.CanApplyTogetherTrait canApplyTogetherTrait = null;
    @ZenProperty
    public ArmorFunctions.CanApplyTogetherEnchantment canApplyTogetherEnchantment = null;
    @ZenProperty
    public ArmorFunctions.ExtraInfo extraInfo = null;
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
    public ConArmTraitRepresentation register() {
        CoTArmorTrait trait = new CoTArmorTrait(identifier, color, maxLevel, countPerLevel);
        trait.onUpdate = this.onUpdate;
        trait.onArmorRepair = this.onArmorRepair;
        trait.onArmorTick = this.onArmorTick;
        trait.getModifications = this.getModifications;
        trait.onItemPickup = this.onItemPickup;
        trait.onHeal = this.onHeal;
        trait.onHurt = this.onHurt;
        trait.onDamaged = this.onDamaged;
        trait.onKnockback = this.onKnockback;
        trait.onFalling = this.onFalling;
        trait.onJumping = this.onJumping;
        trait.onAbility = this.onAbility;
        trait.onArmorEquip = this.onArmorEquip;
        trait.onArmorRemove = this.onArmorRemove;
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

        return new ConArmTraitRepresentation(trait);
    }
}
