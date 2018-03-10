package c4.conarm.armor;

import c4.conarm.armor.common.items.*;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.armor.armor.Boots;
import c4.conarm.armor.armor.Chestplate;
import c4.conarm.armor.armor.Helmet;
import c4.conarm.armor.armor.Leggings;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.armor.common.blocks.BlockArmorForge;
import c4.conarm.armor.common.blocks.BlockSoftMagma;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.armor.ArmorCore;
import com.google.common.collect.Lists;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IPattern;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;

public class ConstructsArmor {

    //Helper
    public static List<Pair<Item, ArmorPart>> armorPartPatterns = Lists.newLinkedList();

    //Augments
    public static ItemTravelBelt travelBelt;
    public static ItemTravelSack travelSack;
    public static ItemTravelGoggles travelGoggles;

    //Blocks
    public static BlockArmorForge armorForge;
    public static BlockSoftMagma softMagma;

    //Armor Parts
    public static ArmorPart helmetCore;
    public static ArmorPart armorTrim;
    public static ArmorPart armorPlate;
    public static ArmorPart chestCore;
    public static ArmorPart leggingsCore;
    public static ArmorPart bootsCore;

    //Armor
    public static ArmorCore helmet;
    public static ArmorCore chestplate;
    public static ArmorCore leggings;
    public static ArmorCore boots;

    //Items
    public static ItemArmoryBook book;
    public static ItemPolishingKit polishingKit;
    public static ItemBase armorKit;
    public static ItemBase speedyKit;
    public static ItemBase diamondKit;
    public static ItemBase emeraldKit;
    public static ItemBase soulboundKit;
    public static ItemBase reinforcementKit;
    public static ItemBase mendingMossKit;
    public static ItemBase resistKit;
    public static ItemBase projResistKit;
    public static ItemBase fireResistKit;
    public static ItemBase blastResistKit;
    public static ItemBase parasiticKit;

    public static void registerArmorParts(IForgeRegistry<Item> registry) {
        helmetCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 3, EntityEquipmentSlot.HEAD), "helmet_core");
        armorTrim = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot), "armor_trim");
        armorPlate = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 10), "armor_plate");
        chestCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 6, EntityEquipmentSlot.CHEST), "chest_core");
        leggingsCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 5, EntityEquipmentSlot.LEGS), "leggings_core");
        bootsCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 3, EntityEquipmentSlot.FEET), "boots_core");
    }

    public static void initModels() {
        travelBelt.initModel();
        travelSack.initModel();
        travelGoggles.initModel();
        book.initModel();
        armorKit.initModel();
        speedyKit.initModel();
        diamondKit.initModel();
        emeraldKit.initModel();
        soulboundKit.initModel();
        reinforcementKit.initModel();
        mendingMossKit.initModel();
        resistKit.initModel();
        blastResistKit.initModel();
        fireResistKit.initModel();
        projResistKit.initModel();
        parasiticKit.initModel();
    }

    public static void registerArmorPieces(IForgeRegistry<Item> registry) {
        helmet = registerArmorPiece(registry, new Helmet(), "helmet");
        chestplate = registerArmorPiece(registry, new Chestplate(), "chestplate");
        leggings = registerArmorPiece(registry, new Leggings(), "leggings");
        boots = registerArmorPiece(registry, new Boots(), "boots");
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        book = ConstructUtils.registerItem(registry, new ItemArmoryBook(), "book");
        armorKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "armor_kit");
        speedyKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "speedy_kit");
        diamondKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "diamond_kit");
        emeraldKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "emerald_kit");
        soulboundKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "soulbound_kit");
        reinforcementKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "reinforcement_kit");
        mendingMossKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "mending_moss_kit");
        resistKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "resistant_kit");
        projResistKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "projectile_resistant_kit");
        fireResistKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "fire_resistant_kit");
        blastResistKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "blast_resistant_kit");
        parasiticKit = ConstructUtils.registerItem(registry, new ArmorKitBase(), "parasitic_kit");
        travelBelt = ConstructUtils.registerItem(registry, new ItemTravelBelt(), "travel_belt");
        travelSack = ConstructUtils.registerItem(registry, new ItemTravelSack(), "travel_sack");
        travelGoggles = ConstructUtils.registerItem(registry, new ItemTravelGoggles(), "travel_goggles");
        polishingKit = (ItemPolishingKit) registerArmorPart(registry, new ItemPolishingKit(), "polishing_kit");
        polishingKit.setCreativeTab(TinkerRegistry.tabParts);
        TinkerRegistry.registerToolPart(polishingKit);
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), polishingKit));
    }

    private static <T extends ArmorCore> T registerArmorPiece(IForgeRegistry<Item> registry, T item, String name) {
        ArmoryRegistry.armor.add(item);
        for(PartMaterialType pmt : item.getRequiredComponents()) {
            for (IToolPart ap : pmt.getPossibleParts()) {
                TinkerRegistry.registerToolPart(ap);
            }
        }
        return ConstructUtils.registerItem(registry, item, name);
    }

    private static ArmorPart registerArmorPart(IForgeRegistry<Item> registry, ArmorPart part, String name) {
        return registerArmorPart(registry, part, name, TinkerTools.pattern);
    }

    private static <T extends Item & IPattern> ArmorPart registerArmorPart(IForgeRegistry<Item> registry, ArmorPart part, String name, T pattern) {
        ArmorPart ret = ConstructUtils.registerItem(registry, part, name);
        ArmoryRegistry.armorParts.add(ret);
        if (pattern != null) {
            armorPartPatterns.add(Pair.of(pattern, ret));
        }
        return ret;
    }
}
