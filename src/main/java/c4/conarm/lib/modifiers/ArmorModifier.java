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

package c4.conarm.lib.modifiers;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.utils.RecipeMatchHolder;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public abstract class ArmorModifier extends ToolModifier implements IArmorModelModifier {

    public ArmorModifier(String identifier, int color) {
        super(identifier + "_armor", color);
        ArmoryRegistry.registerModifier(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {

        return stack.getItem() instanceof TinkersArmor;
    }

    @Override
    public List<List<ItemStack>> getItems() {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        Optional<PriorityQueue<RecipeMatch>> recipes = RecipeMatchHolder.getRecipes(this);
        if (recipes.isPresent()) {
            PriorityQueue<RecipeMatch> recipeMatches = recipes.get();
            for (RecipeMatch rm : recipeMatches) {
                List<ItemStack> in = rm.getInputs();
                if (!in.isEmpty()) {
                    builder.add(in);
                }
            }
        }

        return builder.build();
    }
}
