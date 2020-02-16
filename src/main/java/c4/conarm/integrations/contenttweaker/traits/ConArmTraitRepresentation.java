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

package c4.conarm.integrations.contenttweaker.traits;

import c4.conarm.lib.utils.RecipeMatchHolder;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
@ZenClass("mods.contenttweaker.conarm.ArmorTrait")
@ZenRegister
@ModOnly("contenttweaker")
public class ConArmTraitRepresentation {

    private final ITrait trait;

    public ConArmTraitRepresentation(ITrait trait) {
        this.trait = trait;
    }

    @SuppressWarnings("unused")
    public static ConArmTraitRepresentation getFromString(String identifier) {
        ITrait trait = TinkerRegistry.getTrait(identifier);
        if (trait == null) {
            CraftTweakerAPI.logError("Cannot identify trait " + "<conarmtrait:" + identifier + ">");
            return null;
        }
        return new ConArmTraitRepresentation(trait);
    }


    @ZenMethod
    public void addItem(IIngredient item, @Optional(valueLong = 1) int amountNeeded, @Optional(valueLong = 1) int amountMatched) {

        if (!(trait instanceof IModifier)) {
            CraftTweakerAPI.logError("Cannot add item " + item.toCommandString() + " to trait " + toCommandString());
            return;
        }

        IModifier trait = (IModifier) this.trait;
        if (item instanceof IItemStack) {
            RecipeMatchHolder.addItem(trait, CraftTweakerMC.getItemStack(item), amountNeeded, amountMatched);
        } else if (item instanceof IOreDictEntry) {
            RecipeMatchHolder.addItem(trait, ((IOreDictEntry) item).getName(), amountNeeded, amountMatched);
        } else {
            for (IItemStack itemStack : item.getItems()) {
                addItem(itemStack, amountNeeded, amountMatched);
            }
        }

    }

    @ZenGetter("identifier")
    public String getIdentifier() {
        return trait.getIdentifier();
    }

    @ZenGetter("commandString")
    public String toCommandString() {
        return "<conarmtrait:" + trait.getIdentifier() + ">";
    }

    @ZenMethod
    public ConArmTraitDataRepresentation getData(IItemStack itemStack) {
        if(trait instanceof ModifierTrait)
            return new ConArmTraitDataRepresentation(((ModifierTrait) trait).getData(CraftTweakerMC.getItemStack
                    (itemStack)));
        CraftTweakerAPI.logError("Trait " + trait.getIdentifier() + " is not applicable to the getData function!");
        return null;
    }

    public ITrait getTrait() {
        return trait;
    }
}
