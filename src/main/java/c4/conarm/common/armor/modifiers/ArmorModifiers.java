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

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.modifiers.accessories.*;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.utils.RecipeMatchHolder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.ModExtraTrait;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorModifiers {

    public static Modifier modSpeedy;
    public static Modifier modParasitic;
    public static Modifier modPowerful;
    public static Modifier modTelekinetic;
    public static Modifier modDexterous;
    public static Modifier modEmerald;
    public static Modifier modDiamond;
    public static Modifier modAmphibious;
    public static Modifier modWaterwalk;
    public static Modifier modSticky;
    public static Modifier modShulkerweight;
    public static Modifier modHighStride;
    public static Modifier modGlowing;
    public static Modifier modConcealed;
    public static Modifier modMending;
    public static Modifier modFireResist;
    public static Modifier modProjResist;
    public static Modifier modBlastResist;
    public static Modifier modResist;
    public static Modifier modReinforced;
    public static Modifier modSoulbound;
    public static Modifier modPolished;
    public static Modifier modExtraTrait;

    public static AccessoryModifier modTravelBelt;
    public static AccessoryModifier modTravelPotion;
    public static AccessoryModifier modTravelSack;
    public static AccessoryModifier modTravelGoggles;
    public static AccessoryModifier modTravelNight;
    public static AccessoryModifier modTravelSoul;
    public static AccessoryModifier modTravelSneak;
    public static AccessoryModifier modTravelSlowFall;

    static List<Modifier> polishedMods;
    static List<Modifier> extraTraitMods;

    public static void setupModifiers() {

        modSpeedy = new ModSpeedy(50);
        RecipeMatchHolder.addItem(modSpeedy, "dustRedstone");
        RecipeMatchHolder.addItem(modSpeedy, "blockRedstone", 1, 9);

        modConcealed = new ModConcealed();
        RecipeMatchHolder.addItem(modConcealed, ConstructsRegistry.invisibleInk);

        modPowerful = new ModPowerful();
        RecipeMatchHolder.addItem(modPowerful, ConstructsRegistry.gauntletAttack);

        modDexterous = new ModDexterous();
        RecipeMatchHolder.addItem(modDexterous, ConstructsRegistry.gauntletSpeed);

        modTelekinetic = new ModTelekinetic();
        RecipeMatchHolder.addItem(modTelekinetic, ConstructsRegistry.gauntletReach);

        modGlowing = new ModGlowing();
        RecipeMatchHolder.addRecipeMatch(modGlowing, new RecipeMatch.ItemCombination(1, new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.ENDER_EYE), new ItemStack(Items.GLOWSTONE_DUST)));

        modHighStride = new ModHighStride();
        RecipeMatchHolder.addRecipeMatch(modHighStride, new RecipeMatch.ItemCombination(1, new ItemStack(Blocks.PISTON), new ItemStack(Blocks.PISTON)));

        modShulkerweight = new ModShulkerweight(20);
        RecipeMatchHolder.addItem(modShulkerweight, Items.CHORUS_FRUIT_POPPED);

        modSticky = new ModSticky();
        RecipeMatchHolder.addItem(modSticky, Blocks.WEB, 1);

        modParasitic = new ModParasitic();
        RecipeMatchHolder.addItem(modParasitic, "boneWithered");

        modDiamond = new ModDiamond();
        RecipeMatchHolder.addItem(modDiamond, "gemDiamond");

        modEmerald = new ModEmerald();
        RecipeMatchHolder.addItem(modEmerald, "gemEmerald");

        modSoulbound = new ModSoulbound();
        RecipeMatchHolder.addItem(modSoulbound, Items.NETHER_STAR);

        modMending = new ModMending();
        RecipeMatchHolder.addItem(modMending, TinkerCommons.matMendingMoss, 1, 1);

        modReinforced = new ModReinforced();
        RecipeMatchHolder.addItem(modReinforced, TinkerCommons.matReinforcement, 1, 1);

        modFireResist = new ModResistantType("fire_resistant", 0xea9e32, EnchantmentProtection.Type.FIRE);
        RecipeMatchHolder.addItem(modFireResist, ConstructsRegistry.fireResistMat);

        modBlastResist = new ModResistantType("blast_resistant", 0x862d2d, EnchantmentProtection.Type.EXPLOSION);
        RecipeMatchHolder.addItem(modBlastResist, ConstructsRegistry.blastResistMat);

        modProjResist = new ModResistantType("projectile_resistant", 0x10574b, EnchantmentProtection.Type.PROJECTILE);
        RecipeMatchHolder.addItem(modProjResist, ConstructsRegistry.projResistMat);

        modResist = new ModResistantType("resistant", 0xfff6f6, EnchantmentProtection.Type.ALL);
        RecipeMatchHolder.addItem(modResist, ConstructsRegistry.resistMat);

        modAmphibious = new ModAmphibious();
        RecipeMatchHolder.addRecipeMatch(modAmphibious, new RecipeMatch.ItemCombination(1, new ItemStack(Blocks.GLASS), new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Blocks.GLASS)));

        modWaterwalk = new ModFrostWalker();
        RecipeMatchHolder.addItem(modWaterwalk, ConstructsRegistry.frostySoles);

        modTravelBelt = new ModTravelBelt();
        RecipeMatchHolder.addItem(modTravelBelt, ConstructsRegistry.travelBelt);

        modTravelPotion = new ModTravelPotion();
        RecipeMatchHolder.addItem(modTravelPotion, ConstructsRegistry.travelPotion);

        modTravelSack = new ModTravelSack();
        RecipeMatchHolder.addItem(modTravelSack, ConstructsRegistry.travelSack);

        modTravelGoggles = new ModTravelGoggles();
        RecipeMatchHolder.addItem(modTravelGoggles, ConstructsRegistry.travelGoggles);

        modTravelNight = new ModTravelNight();
        RecipeMatchHolder.addItem(modTravelNight, ConstructsRegistry.travelNight);

        modTravelSoul = new ModTravelSoul();
        RecipeMatchHolder.addItem(modTravelSoul, ConstructsRegistry.travelSoul);

        modTravelSlowFall = new ModTravelSlowFall();
        RecipeMatchHolder.addItem(modTravelSlowFall, ConstructsRegistry.travelSlowFall);

        modTravelSneak = new ModTravelSneak();
        RecipeMatchHolder.addItem(modTravelSneak, ConstructsRegistry.travelSneak);

        modPolished = new ModPolishedDisplay();
        ArmoryRegistry.registerModifier(modPolished);

        modExtraTrait = new ModExtraArmorTraitDisplay();
        ArmoryRegistry.registerModifier(modExtraTrait);

        ArmoryRegistry.registerModifier(TinkerModifiers.modCreative.getIdentifier(), TinkerModifiers.modCreative);
        RecipeMatchHolder.addItem(TinkerModifiers.modCreative, TinkerCommons.matCreativeModifier, 1, 1);
    }

    private static Map<String, ModExtraArmorTrait> extraTraitLookup = new HashMap<>();

    public static void registerPolishedModifiers() {
        polishedMods = Lists.newArrayList();
        for (Material mat : TinkerRegistry.getAllMaterialsWithStats(ArmorMaterialType.PLATES)) {
            ModPolished mod = new ModPolished(mat);
            polishedMods.add(mod);
            ArmoryRegistry.registerModifier(mod);
        }
    }

    public static void registerExtraTraitModifiers() {
        TinkerRegistry.getAllMaterials().forEach(ArmorModifiers::registerExtraTraitModifiers);
        extraTraitMods = Lists.newArrayList(extraTraitLookup.values());
    }

    private static void registerExtraTraitModifiers(Material material) {
        ArmoryRegistry.getArmor().forEach(armor -> registerExtraTraitModifiers(material, armor));
    }

    private static void registerExtraTraitModifiers(Material material, ArmorCore armor) {
        armor.getRequiredComponents().forEach(pmt -> registerExtraTraitModifiers(material, armor, pmt));
    }

    private static void registerExtraTraitModifiers(Material material, ArmorCore armor, PartMaterialType partMaterialType) {
        partMaterialType.getPossibleParts().forEach(part -> registerExtraTraitModifiers(material, armor, partMaterialType, part));
    }

    @SuppressWarnings("unchecked")
    private static <T extends Item & IToolPart> void registerExtraTraitModifiers(Material material, ArmorCore armor, PartMaterialType partMaterialType, IToolPart armorPart) {
        if(armorPart instanceof Item) {
            Collection<ITrait> traits = partMaterialType.getApplicableTraitsForMaterial(material);
            if(!traits.isEmpty()) {
                final Collection<ITrait> traits2 = ImmutableSet.copyOf(traits);
                String identifier = ModExtraTrait.generateIdentifier(material, traits2);
                ModExtraArmorTrait mod = extraTraitLookup.computeIfAbsent(identifier, id -> new ModExtraArmorTrait(material, traits2, identifier));
                mod.addCombination(armor, (T) armorPart);
            }
        }
    }
}
