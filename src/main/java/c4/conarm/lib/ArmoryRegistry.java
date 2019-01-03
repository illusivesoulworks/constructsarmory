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

package c4.conarm.lib;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TLinkedHashSet;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.TinkerAPIException;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.*;

public class ArmoryRegistry {

    public static final Set<ArmorCore> armor = new TLinkedHashSet<>();
    public static final Set<ArmorPart> armorParts = new TLinkedHashSet<>();
    private static final Map<String, IModifier> armorModifiers = new THashMap<>();
    public static final Set<ArmorCore> armorCrafting = Sets.newLinkedHashSet();
    private static final EnumMap<EntityEquipmentSlot, Map<String, ArmorCore>> armorAppearances = Maps.newEnumMap(EntityEquipmentSlot.class);

    private ArmoryRegistry() {}

    public static void registerAllArmorForging() {
        for (ArmorCore armor : armor) {
            registerArmorForging(armor);
        }
    }

    public static Set<ArmorCore> getArmor() {
        return ImmutableSet.copyOf(armor);
    }

    public static void registerArmorForging(ArmorCore armor) {
        armorCrafting.add(armor);
    }

    public static Set<ArmorCore> getArmorCrafting() {
        return ImmutableSet.copyOf(armorCrafting);
    }

    public static void registerModifier(IModifier modifier) {
        registerModifierAlias(modifier, modifier.getIdentifier());
    }

    public static void registerModifier(String identifier, IModifier modifier) {
        registerModifierAlias(modifier, identifier);
    }

    /** Registers an alternate name for a modifier. This is used for multi-level modifiers/traits where multiple exist, but one specific is needed for access */
    public static void registerModifierAlias(IModifier modifier, String alias) {
        if(armorModifiers.containsKey(alias)) {
            throw new TinkerAPIException("Trying to register a modifier with the name " + alias + " but it already is registered");
        }
        if(new TinkerRegisterEvent.ModifierRegisterEvent(modifier).fire()) {
            armorModifiers.put(alias, modifier);
        }
        else {
            ConstructsArmory.logger.debug("Registration of modifier " + alias + " has been cancelled by event");
        }
    }

    public static void addArmor(ArmorCore item, EntityEquipmentSlot slotIn) {
        armor.add(item);
        Map<String, ArmorCore> appearances = armorAppearances.computeIfAbsent(slotIn, s -> Maps.newHashMap());
        String identifier = item.getAppearanceName();
        if (appearances.containsKey(identifier)) {
            throw new ConArmAPIException("Trying to register an armor appearance with the name " + identifier + " in slot " + slotIn.getName() + " but it is already registered");
        }
        appearances.put(identifier, item);
    }

    public static IModifier getArmorModifier(String identifier) {
        return armorModifiers.get(identifier);
    }

    public static Collection<IModifier> getAllArmorModifiers() {
        return ImmutableList.copyOf(armorModifiers.values());
    }

    public static List<String> getAppearancesForSlot(EntityEquipmentSlot slotIn) {
        return ImmutableList.copyOf(armorAppearances.get(slotIn).keySet());
    }
}
