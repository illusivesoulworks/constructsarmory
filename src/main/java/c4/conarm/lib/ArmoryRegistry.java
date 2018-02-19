package c4.conarm.lib;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TLinkedHashSet;
import slimeknights.tconstruct.library.TinkerAPIException;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tinkering.Category;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ArmoryRegistry {

    public static final Category ARMOR = new Category("armor");
    public static final Set<ArmorCore> armor = new TLinkedHashSet<>();
    public static final Set<ArmorPart> armorParts = new TLinkedHashSet<>();
    private static final Map<String, IModifier> armorModifiers = new THashMap<>();
    public static final Set<ArmorCore> armorForgeCrafting = Sets.newLinkedHashSet();

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
        armorForgeCrafting.add(armor);
    }

    public static Set<ArmorCore> getArmorForgeCrafting() {
        return ImmutableSet.copyOf(armorForgeCrafting);
    }

    public static void registerModifier(IModifier modifier) {
        registerModifierAlias(modifier, modifier.getIdentifier());
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

    public static IModifier getArmorModifier(String identifier) {
        return armorModifiers.get(identifier);
    }

    public static Collection<IModifier> getAllArmorModifiers() {
        return ImmutableList.copyOf(armorModifiers.values());
    }
}
