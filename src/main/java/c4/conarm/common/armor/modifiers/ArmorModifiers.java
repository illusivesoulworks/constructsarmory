/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
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

    public static Modifier modSpeedy = new ModSpeedy(50);
    public static Modifier modParasitic = new ModParasitic();
    public static Modifier modEmerald = new ModEmerald();
    public static Modifier modDiamond = new ModDiamond();
    public static Modifier modAmphibious = new ModAmphibious();
    public static Modifier modWaterwalk = new ModFrostWalker();
    public static Modifier modSticky = new ModSticky();
    public static Modifier modShulkerweight = new ModShulkerweight(20);
    public static Modifier modHighStride = new ModHighStride();
    public static Modifier modMending = new ModMending();
    public static Modifier modFireResist = new ModResistantType("fire_resistant", 0xea9e32, EnchantmentProtection.Type.FIRE);
    public static Modifier modProjResist = new ModResistantType("projectile_resistant", 0x10574b, EnchantmentProtection.Type.PROJECTILE);
    public static Modifier modBlastResist = new ModResistantType("blast_resistant", 0x862d2d, EnchantmentProtection.Type.EXPLOSION);
    public static Modifier modResist = new ModResistantType("resistant", 0xfff6f6, EnchantmentProtection.Type.ALL);
    public static Modifier modReinforced = new ModReinforced();
    public static Modifier modSoulbound = new ModSoulbound();
    public static Modifier modPolished = new ModPolishedDisplay();
    public static Modifier modExtraTrait = new ModExtraArmorTraitDisplay();

    public static AccessoryModifier modTravelBelt = new ModTravelBelt();
    public static AccessoryModifier modTravelSack = new ModTravelSack();
    public static AccessoryModifier modTravelGoggles = new ModTravelGoggles();
    public static AccessoryModifier modTravelNight = new ModTravelNight();

    static List<Modifier> polishedMods;
    static List<Modifier> extraTraitMods;

    public static void setupModifiers() {

        ArmoryRegistry.registerModifier(modHighStride);
        RecipeMatchHolder.addRecipeMatch(modHighStride, new RecipeMatch.ItemCombination(1, new ItemStack(Blocks.PISTON), new ItemStack(Blocks.PISTON)));

        ArmoryRegistry.registerModifier(modShulkerweight);
        RecipeMatchHolder.addItem(modShulkerweight, Items.CHORUS_FRUIT_POPPED);

        ArmoryRegistry.registerModifier(modSticky);
        RecipeMatchHolder.addItem(modSticky, Blocks.WEB, 1);

        ArmoryRegistry.registerModifier(modSpeedy);
        RecipeMatchHolder.addItem(modSpeedy, "dustRedstone");
        RecipeMatchHolder.addItem(modSpeedy, "blockRedstone", 1, 9);

        ArmoryRegistry.registerModifier(modParasitic);
        RecipeMatchHolder.addItem(modParasitic, "boneWithered");

        ArmoryRegistry.registerModifier(modDiamond);
        RecipeMatchHolder.addItem(modDiamond, "gemDiamond");

        ArmoryRegistry.registerModifier(modEmerald);
        RecipeMatchHolder.addItem(modEmerald, "gemEmerald");

        ArmoryRegistry.registerModifier(modSoulbound);
        RecipeMatchHolder.addItem(modSoulbound, Items.NETHER_STAR);

        ArmoryRegistry.registerModifier(modMending);
        RecipeMatchHolder.addItem(modMending, TinkerCommons.matMendingMoss, 1, 1);

        ArmoryRegistry.registerModifier(modReinforced);
        RecipeMatchHolder.addItem(modReinforced, TinkerCommons.matReinforcement, 1, 1);

        ArmoryRegistry.registerModifier(modFireResist);
        RecipeMatchHolder.addItem(modFireResist, ConstructsRegistry.fireResistMat);

        ArmoryRegistry.registerModifier(modBlastResist);
        RecipeMatchHolder.addItem(modBlastResist, ConstructsRegistry.blastResistMat);

        ArmoryRegistry.registerModifier(modProjResist);
        RecipeMatchHolder.addItem(modProjResist, ConstructsRegistry.projResistMat);

        ArmoryRegistry.registerModifier(modResist);
        RecipeMatchHolder.addItem(modResist, ConstructsRegistry.resistMat);

        ArmoryRegistry.registerModifier(modAmphibious);
        RecipeMatchHolder.addRecipeMatch(modAmphibious, new RecipeMatch.ItemCombination(1, new ItemStack(Blocks.GLASS), new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Blocks.GLASS)));

        ArmoryRegistry.registerModifier(modWaterwalk);
        RecipeMatchHolder.addRecipeMatch(modWaterwalk, new RecipeMatch.ItemCombination(1, new ItemStack(Blocks.WATERLILY), new ItemStack(Blocks.PACKED_ICE), new ItemStack(Blocks.WATERLILY), new ItemStack(Blocks.PACKED_ICE)));

        ArmoryRegistry.registerModifier(modTravelBelt);
        RecipeMatchHolder.addItem(modTravelBelt, ConstructsRegistry.travelBelt);

        ArmoryRegistry.registerModifier(modTravelSack);
        RecipeMatchHolder.addItem(modTravelSack, ConstructsRegistry.travelSack);

        ArmoryRegistry.registerModifier(modTravelGoggles);
        RecipeMatchHolder.addItem(modTravelGoggles, ConstructsRegistry.travelGoggles);

        ArmoryRegistry.registerModifier(modTravelNight);
        RecipeMatchHolder.addItem(modTravelNight, ConstructsRegistry.travelNight);

        ArmoryRegistry.registerModifier(modPolished);
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
        extraTraitMods.forEach(ArmoryRegistry::registerModifier);
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
