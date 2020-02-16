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

package c4.conarm.lib.utils;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class RecipeMatchHolder {

    private static final Map<IModifier, PriorityQueue<RecipeMatch>> recipes = Maps.newHashMap();

    public static Optional<PriorityQueue<RecipeMatch>> getRecipes(IModifier modifier) {

        return Optional.ofNullable(recipes.get(modifier));
    }

    public static Optional<RecipeMatch.Match> matches(IModifier modifier, NonNullList<ItemStack> stacks) {

        if (recipes.get(modifier) != null) {
            PriorityQueue<RecipeMatch> matchHolder = recipes.get(modifier);
            for (RecipeMatch recipe : matchHolder) {
                Optional<RecipeMatch.Match> match = recipe.matches(stacks);
                if (match.isPresent()) {
                    return match;
                }
            }
        }

        return Optional.empty();
    }

    public static void addRecipeMatch(IModifier modifier, RecipeMatch recipe) {
        recipes.putIfAbsent(modifier, new PriorityQueue<>(1, RecipeComparator.INSTANCE));
        PriorityQueue<RecipeMatch> recipeMatches = recipes.get(modifier);
        if (recipeMatches != null) {
            recipeMatches.add(recipe);
        }
    }

    /**
     * Associates an oredict entry with this material. Used for repairing and other.
     *
     * @param oredictItem   Oredict-String
     * @param amountNeeded  How many of this item are needed to count as one full material item.
     * @param amountMatched If both item and amount are present, how often did they match?
     */
    public static void addItem(IModifier modifier, String oredictItem, int amountNeeded, int amountMatched) {
        addRecipeMatch(modifier, new RecipeMatch.Oredict(oredictItem, amountNeeded, amountMatched));
    }

    public static void addItem(IModifier modifier, String oredictItem) {
        addItem(modifier, oredictItem, 1, 1);
    }

    /**
     * Associates a block with this material. Used for repairing and other.
     *
     * @param amountMatched For how many matches the block counts (e.g. redstone dust = 1 match, Redstone block = 9)
     */
    public static void addItem(IModifier modifier, Block block, int amountMatched) {
        addRecipeMatch(modifier, new RecipeMatch.Item(new ItemStack(block), 1, amountMatched));
    }

    /**
     * Associates an item entry with this material. Used for repairing and other.
     *
     * @param item          The item
     * @param amountNeeded  How many of this item are needed to count as one full material item.
     * @param amountMatched If both item and amount are present, how often did they match?
     */
    public static void addItem(IModifier modifier, Item item, int amountNeeded, int amountMatched) {
        addRecipeMatch(modifier, new RecipeMatch.Item(new ItemStack(item), amountNeeded, amountMatched));
    }

    /**
     * Associates an item entry with this material. Used for repairing and other.
     *
     * @param item          The item
     * @param amountNeeded  How many of this item are needed to count as one full material item.
     * @param amountMatched If both item and amount are present, how often did they match?
     */
    public static void addItem(IModifier modifier, ItemStack item, int amountNeeded, int amountMatched) {
        addRecipeMatch(modifier, new RecipeMatch.Item(item, amountNeeded, amountMatched));
    }

    /**
     * Associates an item with this material. Used for repairing and other.
     */
    public static void addItem(IModifier modifier, Item item) {
        addItem(modifier, item, 1, 1);
    }

    private static class RecipeComparator implements Comparator<RecipeMatch> {

        public static RecipeComparator INSTANCE = new RecipeComparator();

        private RecipeComparator() {
        }

        @Override
        public int compare(RecipeMatch o1, RecipeMatch o2) {
            return o2.amountMatched - o1.amountMatched;
        }
    }
}
