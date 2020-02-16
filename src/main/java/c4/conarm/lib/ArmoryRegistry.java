/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
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
