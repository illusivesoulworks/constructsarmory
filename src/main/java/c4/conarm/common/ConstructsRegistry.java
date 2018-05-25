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

package c4.conarm.common;

import c4.conarm.common.blocks.BlockArmorForge;
import c4.conarm.common.blocks.BlockSoftObsidian;
import c4.conarm.common.items.*;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.common.items.armor.Boots;
import c4.conarm.common.items.armor.Chestplate;
import c4.conarm.common.items.armor.Helmet;
import c4.conarm.common.items.armor.Leggings;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.common.blocks.BlockArmorStation;
import c4.conarm.lib.utils.ConstructUtils;
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

public class ConstructsRegistry {

    //Helper
    public static List<Pair<Item, ArmorPart>> armorPartPatterns = Lists.newLinkedList();

    //Accessories
    public static AccessoryBase travelBelt;
    public static AccessoryBase travelPotion;
    public static AccessoryBase travelSack;
    public static AccessoryBase travelGoggles;
    public static AccessoryBase travelNight;
    public static AccessoryBase travelSoul;

    //Blocks
    public static BlockArmorForge armorForge;
    public static BlockArmorStation armorStation;
    public static BlockSoftObsidian softObsidian;

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
    public static ItemBase resistMat;
    public static ItemBase fireResistMat;
    public static ItemBase projResistMat;
    public static ItemBase blastResistMat;

    public static void registerArmorParts(IForgeRegistry<Item> registry) {
        helmetCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 4, EntityEquipmentSlot.HEAD), "helmet_core");
        armorTrim = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot), "armor_trim");
        armorPlate = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 3), "armor_plate");
        chestCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 6, EntityEquipmentSlot.CHEST), "chest_core");
        leggingsCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 5, EntityEquipmentSlot.LEGS), "leggings_core");
        bootsCore = registerArmorPart(registry, new ArmorPart(Material.VALUE_Ingot * 4, EntityEquipmentSlot.FEET), "boots_core");
    }

    public static void initModels() {
        travelBelt.initModel();
        travelPotion.initModel();
        travelSack.initModel();
        travelGoggles.initModel();
        travelNight.initModel();
        travelSoul.initModel();
        book.initModel();
        resistMat.initModel();
        fireResistMat.initModel();
        projResistMat.initModel();
        blastResistMat.initModel();
    }

    public static void registerArmorPieces(IForgeRegistry<Item> registry) {
        helmet = registerArmorPiece(registry, new Helmet("classic"), "helmet");
        chestplate = registerArmorPiece(registry, new Chestplate("classic"), "chestplate");
        leggings = registerArmorPiece(registry, new Leggings("classic"), "leggings");
        boots = registerArmorPiece(registry, new Boots("classic"), "boots");
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        book = ConstructUtils.registerItem(registry, new ItemArmoryBook(), "book");
        resistMat = ConstructUtils.registerItem(registry, new ItemResistantMat(), "resist_mat");
        fireResistMat = ConstructUtils.registerItem(registry, new ItemResistantMat(), "resist_mat_fire");
        projResistMat = ConstructUtils.registerItem(registry, new ItemResistantMat(), "resist_mat_proj");
        blastResistMat = ConstructUtils.registerItem(registry, new ItemResistantMat(), "resist_mat_blast");
        travelBelt = ConstructUtils.registerItem(registry, new AccessoryBase(EntityEquipmentSlot.LEGS), "travel_belt");
        travelPotion = ConstructUtils.registerItem(registry, new AccessoryBase(EntityEquipmentSlot.LEGS), "travel_potion");
        travelSack = ConstructUtils.registerItem(registry, new AccessoryBase(EntityEquipmentSlot.CHEST), "travel_sack");
        travelGoggles = ConstructUtils.registerItem(registry, new AccessoryBase(EntityEquipmentSlot.HEAD), "travel_goggles");
        travelNight = ConstructUtils.registerItem(registry, new AccessoryBase(EntityEquipmentSlot.HEAD), "travel_night");
        travelSoul = ConstructUtils.registerItem(registry, new AccessoryBase(EntityEquipmentSlot.HEAD), "travel_soul");
        polishingKit = (ItemPolishingKit) registerArmorPart(registry, new ItemPolishingKit(), "polishing_kit");
        polishingKit.setCreativeTab(TinkerRegistry.tabParts);
        TinkerRegistry.registerToolPart(polishingKit);
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), polishingKit));
    }

    private static <T extends ArmorCore> T registerArmorPiece(IForgeRegistry<Item> registry, T item, String name) {
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
