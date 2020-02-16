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

package c4.conarm.common;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ShapelessPotionRecipe extends ShapelessRecipes {

    private NonNullList<PotionType> types;

    public ShapelessPotionRecipe(NonNullList<Ingredient> ings, @Nonnull ItemStack output) {
        super("", output, ings);
        types = NonNullList.create();
        for (Ingredient ing : recipeItems) {
            ItemStack stack = ing.getMatchingStacks()[0];
            if (stack.getItem() instanceof ItemPotion) {
                types.add(PotionUtils.getPotionFromItem(stack));
            }
        }
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {

        NonNullList<PotionType> typeCheck = NonNullList.create();
        typeCheck.addAll(types);

        for (int i = 0; i < inv.getHeight(); ++i)
        {
            for (int j = 0; j < inv.getWidth(); ++j)
            {
                ItemStack itemstack = inv.getStackInRowAndColumn(j, i);

                if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemPotion)
                {
                    if (typeCheck.isEmpty()) {
                        return false;
                    }

                    for (Ingredient ing : recipeItems) {
                        ItemStack stack = ing.getMatchingStacks()[0];
                        if (PotionUtils.getPotionFromItem(stack) == PotionUtils.getPotionFromItem(itemstack)) {
                            typeCheck.remove(PotionUtils.getPotionFromItem(stack));
                        }
                    }
                }
            }
        }

        return typeCheck.isEmpty() && super.matches(inv, worldIn);
    }
}
