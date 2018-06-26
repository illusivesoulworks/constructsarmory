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

package c4.conarm.integrations.contenttweaker.materials;

import c4.conarm.integrations.contenttweaker.traits.CoTArmorTraitBuilder;
import c4.conarm.integrations.contenttweaker.traits.ConArmTraitRepresentation;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.CoTTConMaterialIntegration;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.TConMaterialRepresentation;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.CoTTrait;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.CoTTraitBuilder;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.CoTRecipeMatch;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.Functions;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;
import stanhebben.zenscript.util.Pair;

import java.util.ArrayList;
import java.util.List;

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
@ZenClass("mods.contenttweaker.conarm.ExtendedMaterialBuilder")
@ZenRegister
@ModOnly("contenttweaker")
public class CoTConArmMaterialBuilder {

    @ZenProperty
    public String identifier;

    @ZenProperty
    public int color = 0xffffff;

    @ZenProperty
    public boolean hidden = false;

    @ZenProperty
    public ILiquidStack liquid = null;

    @ZenProperty
    public boolean craftable = false;

    @ZenProperty
    public boolean castable = false;

    @ZenProperty
    public IItemStack representativeItem = null;

    @ZenProperty
    public IOreDictEntry representativeOre = null;

    @ZenProperty
    public IItemStack shard = null;

    @ZenProperty
    public String localizedName = null;

    @ZenProperty
    public Functions.ItemLocalizer itemLocalizer = null;

    private List<CoTRecipeMatch> itemMatches = new ArrayList<>();
    private List<Pair<String, String>> materialTraits = new ArrayList<>();

    private HeadMaterialStats headMaterialStats = null;
    private HandleMaterialStats handleMaterialStats = null;
    private ExtraMaterialStats extraMaterialStats = null;
    private BowMaterialStats bowMaterialStats = null;
    private BowStringMaterialStats bowStringMaterialStats = null;
    private ArrowShaftMaterialStats arrowShaftMaterialStats = null;
    private FletchingMaterialStats fletchingMaterialStats = null;
    private ProjectileMaterialStats projectileMaterialStats = null;

    private CoreMaterialStats coreMaterialStats = null;
    private PlatesMaterialStats platesMaterialStats = null;
    private TrimMaterialStats trimMaterialStats = null;

    public CoTConArmMaterialBuilder(String identifier) {
        this.identifier = identifier;
    }

    @ZenMethod
    public static CoTConArmMaterialBuilder create(String identifier) {
        return new CoTConArmMaterialBuilder(identifier);
    }

    @ZenMethod
    public void addItem(IIngredient ingredient, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 144) int amountMatched) {
        itemMatches.add(new CoTRecipeMatch(ingredient, amountMatched, amountNeeded));
    }

    @ZenMethod
    public void removeItem(IItemStack itemStack) {
        itemMatches.removeIf(coTRecipeMatch -> coTRecipeMatch.matches(itemStack));
    }

    @ZenMethod
    public void addMaterialTrait(ITrait trait, @Optional String dependency) {
        addMaterialTrait(trait.getIdentifier(), dependency);
    }

    @ZenMethod
    public void addMaterialTrait(TConTraitRepresentation trait, @Optional String dependency) {
        addMaterialTrait(trait.getTrait(), dependency);
    }

    @ZenMethod
    public void addMaterialTrait(ConArmTraitRepresentation trait, @Optional String dependency) {
        addMaterialTrait(trait.getTrait(), dependency);
    }

    @ZenMethod
    public void addMaterialTrait(CoTTraitBuilder traitBuilder, @Optional String dependency) {
        addMaterialTrait(traitBuilder.identifier, dependency);
    }

    @ZenMethod
    public void addMaterialTrait(CoTArmorTraitBuilder traitBuilder, @Optional String dependency) {
        addMaterialTrait(traitBuilder.identifier, dependency);
    }

    @ZenMethod
    public void addMaterialTrait(String identifier, @Optional String dependency) {
        materialTraits.add(new Pair<>(identifier, dependency));
    }

    @ZenMethod
    public void removeMaterialTrait(String identifier, @Optional String dependency) {
        materialTraits.removeIf(traitPair -> identifier.equals(traitPair.getKey()) && (dependency == null || dependency.equals(traitPair.getValue())));
    }

    @ZenMethod
    public void addHeadMaterialStats(int durability, float miningSpeed, float attackDamage, int harvestLevel) {
        this.headMaterialStats = new HeadMaterialStats(durability, miningSpeed, attackDamage, harvestLevel);
    }

    @ZenMethod
    public void removeHeadMaterialStats() {
        headMaterialStats = null;
    }

    @ZenMethod
    public void addHandleMaterialStats(float modifier, int durability) {
        this.handleMaterialStats = new HandleMaterialStats(modifier, durability);
    }

    @ZenMethod
    public void removeHandleMaterialStats() {
        handleMaterialStats = null;
    }

    @ZenMethod
    public void addExtraMaterialStats(int extraDurability) {
        this.extraMaterialStats = new ExtraMaterialStats(extraDurability);
    }

    @ZenMethod
    public void removeExtraMaterialStats() {
        extraMaterialStats = null;
    }

    @ZenMethod
    public void addBowMaterialStats(float drawSpeed, float range, float bonusDamage) {
        this.bowMaterialStats = new BowMaterialStats(drawSpeed, range, bonusDamage);
    }

    @ZenMethod
    public void removeBowMaterialStats() {
        bowMaterialStats = null;
    }

    @ZenMethod
    public void addBowStringMaterialStats(float modifier) {
        this.bowStringMaterialStats = new BowStringMaterialStats(modifier);
    }

    @ZenMethod
    public void removeBowStringMaterialStats() {
        bowStringMaterialStats = null;
    }

    @ZenMethod
    public void addArrowShaftMaterialStats(float modifier, int bonusAmmo) {
        this.arrowShaftMaterialStats = new ArrowShaftMaterialStats(modifier, bonusAmmo);
    }

    @ZenMethod
    public void removeArrowShaftMaterialStats() {
        arrowShaftMaterialStats = null;
    }

    @ZenMethod
    public void addFletchingMaterialStats(float accuracy, float modifier) {
        this.fletchingMaterialStats = new FletchingMaterialStats(accuracy, modifier);
    }

    @ZenMethod
    public void removeFletchingMaterialStats() {
        fletchingMaterialStats = null;
    }

    @ZenMethod
    public void addProjectileMaterialStats() {
        this.projectileMaterialStats = new ProjectileMaterialStats();
    }

    @ZenMethod
    public void removeProjectileMaterialStats() {
        this.projectileMaterialStats = null;
    }

    @ZenMethod
    public void addCoreMaterialStats(float durability, float defense) {
        this.coreMaterialStats = new CoreMaterialStats(durability, defense);
    }

    @ZenMethod
    public void removeCoreMaterialStats() {
        this.coreMaterialStats = null;
    }

    @ZenMethod
    public void addPlatesMaterialStats(float modifier, float durability, float toughness) {
        this.platesMaterialStats = new PlatesMaterialStats(modifier, durability, toughness);
    }

    @ZenMethod
    public void removePlatesMaterialStats() {
        this.platesMaterialStats = null;
    }

    @ZenMethod
    public void addTrimMaterialStats(float durability) {
        this.trimMaterialStats = new TrimMaterialStats(durability);
    }

    @ZenMethod
    public void removeTrimMaterialStats() {
        this.trimMaterialStats = null;
    }

    @ZenMethod
    public TConMaterialRepresentation register() {
        CoTConArmMaterial material = new CoTConArmMaterial(identifier, color);

        //MaterialStats, if available
        if (headMaterialStats != null) {
            material.addStats(headMaterialStats);
        }
        if (handleMaterialStats != null) {
            material.addStats(handleMaterialStats);
        }
        if (extraMaterialStats != null) {
            material.addStats(extraMaterialStats);
        }
        if (bowMaterialStats != null) {
            material.addStats(bowMaterialStats);
        }
        if (bowStringMaterialStats != null) {
            material.addStats(bowStringMaterialStats);
        }
        if (arrowShaftMaterialStats != null) {
            material.addStats(arrowShaftMaterialStats);
        }
        if (fletchingMaterialStats != null) {
            material.addStats(fletchingMaterialStats);
        }
        if (projectileMaterialStats != null) {
            material.addStats(projectileMaterialStats);
        }
        if (coreMaterialStats != null) {
            material.addStats(coreMaterialStats);
        }
        if (platesMaterialStats != null) {
            material.addStats(platesMaterialStats);
        }
        if (trimMaterialStats != null) {
            material.addStats(trimMaterialStats);
        }

        if (liquid != null) {
            material.liquid = liquid;
        }

        //Casting/Crafting
        material.setCastable(castable);
        material.setCraftable(craftable);
        material.hidden = hidden;


        //Items
        itemMatches.forEach(material::addItemMatch);

        if (representativeItem != null) {
            material.representativeItem = this.representativeItem;
        }
        if (representativeOre != null) {
            material.setRepresentativeItem(representativeOre.getName());
        }
        if (shard != null) {
            material.setShard(CraftTweakerMC.getItemStack(shard));
        }


        //Traits
        for (Pair<String, String> pair : materialTraits) {
            ITrait trait = TinkerRegistry.getTrait(pair.getKey());
            material.addTrait(trait, pair.getValue());
        }

        material.itemLocalizer = this.itemLocalizer;
        material.localizedName = this.localizedName;

        TinkerRegistry.addMaterial(material);
        TinkerRegistry.integrate(new CoTTConMaterialIntegration(material));
        return material.thisMaterial;
    }
}
