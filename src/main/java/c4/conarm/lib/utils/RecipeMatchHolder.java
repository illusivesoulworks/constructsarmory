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

package c4.conarm.lib.utils;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.mantle.util.RecipeMatchRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class RecipeMatchHolder {

    private static final Map<IModifier, PriorityQueue<RecipeMatch>> recipes = Maps.newHashMap();

    public static Optional<PriorityQueue<RecipeMatch>> getRecipes(IModifier modifier) {

        return Optional.of(recipes.get(modifier));
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
