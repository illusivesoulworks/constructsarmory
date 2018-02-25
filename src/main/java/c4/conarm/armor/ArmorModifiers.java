package c4.conarm.armor;

import c4.conarm.armor.modifiers.*;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

//    public static Modifier modSilkstep = new ModSilkstep();
    public static Modifier modSpeedy = new ModSpeedy();
    public static Modifier modParasitic = new ModParasitic();
    public static Modifier modEmerald = new ModEmerald();
    public static Modifier modDiamond = new ModDiamond();
//    public static Modifier modSticky = new ModSticky();
//    public static Modifier modLowGravity = new ModLowGravity();
    public static Modifier modMending = new ModMending();
    public static Modifier modFireResist = new ModFireResistant();
    public static Modifier modProjResist = new ModProjectileResistant();
    public static Modifier modBlastResist = new ModBlastResistant();
    public static Modifier modResist = new ModResistant();
    public static Modifier modReinforced = new ModReinforced();
    public static Modifier modSoulbound = new ModSoulbound();
//    public static Modifier modFrostStep = new ModFrostStep();
//    public static Modifier modMagmaStep = new ModMagmaStep();
//    public static Modifier modArthopodWard = new ModAntiMonsterResistance("arthopod_ward", 0x61ba49, 5, 24, EnumCreatureAttribute.ARTHROPOD);
//    public static Modifier modHolyWard = new ModAntiMonsterResistance("holy_ward", 0xe8d500, 5, 24, EnumCreatureAttribute.UNDEAD);

    public static List<Modifier> extraTraitMods;

    public static void setupModifiers() {

//        ArmoryRegistry.registerModifier(modSilkstep);
//        modSilkstep.addItem(TinkerCommons.matSilkyJewel, 2, 1);
//
        ArmoryRegistry.registerModifier(TinkerModifiers.modHaste.getIdentifier(), modSpeedy);

        ArmoryRegistry.registerModifier(TinkerModifiers.modNecrotic.getIdentifier(), modParasitic);

        ArmoryRegistry.registerModifier(TinkerModifiers.modDiamond.getIdentifier(), ArmorModifiers.modDiamond);

        ArmoryRegistry.registerModifier(TinkerModifiers.modEmerald.getIdentifier(), ArmorModifiers.modEmerald);
//        modEmerald.addItem("gemEmerald");
//
//        ArmoryRegistry.registerModifier(modSticky);
//        modSticky.addItem(Blocks.WEB, 1);
//
//        ArmoryRegistry.registerModifier(modLowGravity);
//        modLowGravity.addItem(Items.CHORUS_FRUIT_POPPED);
//
        ArmoryRegistry.registerModifier(TinkerModifiers.modMendingMoss.getIdentifier(), modMending);

        ArmoryRegistry.registerModifier(modFireResist);
        modFireResist.addItem(Items.BLAZE_POWDER);

        ArmoryRegistry.registerModifier(modBlastResist);
        modBlastResist.addItem(Items.BRICK);

        ArmoryRegistry.registerModifier(modProjResist);
        modProjResist.addItem(Items.FEATHER);

        ArmoryRegistry.registerModifier(modResist);
        modResist.addItem(Blocks.OBSIDIAN, 1);

        ArmoryRegistry.registerModifier(TinkerModifiers.modReinforced.getIdentifier(), ArmorModifiers.modReinforced);
//
//        ArmoryRegistry.registerModifier(modFrostStep);
//        modFrostStep.addItem(Blocks.PACKED_ICE, 1);
//
//        ArmoryRegistry.registerModifier(modMagmaStep);
//        modMagmaStep.addItem(Blocks.MAGMA, 1);
//
//        ArmoryRegistry.registerModifier(modArthopodWard);
//        modArthopodWard.addItem(Items.FERMENTED_SPIDER_EYE);
//
//        ArmoryRegistry.registerModifier(modHolyWard);
//        modHolyWard.addItem(TinkerCommons.consecratedSoil, 1, 1);

        ArmoryRegistry.registerModifier(TinkerModifiers.modSoulbound.getIdentifier(), ArmorModifiers.modSoulbound);
    }

    private static Map<String, ModExtraArmorTrait> extraTraitLookup = new HashMap<>();

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
