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

import c4.conarm.lib.utils.ConstructUtils;
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
