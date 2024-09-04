/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryItems;

import java.util.function.Consumer;

public class ArmorRecipeProvider extends RecipeProvider implements IMaterialRecipeHelper, IToolRecipeHelper {

    public ArmorRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        String folder = "tools/building/";

        ConstructsArmoryItems.MATERIAL_ARMOR.values().forEach(item -> toolBuilding(consumer, item, folder));
        String partFolder = "tools/parts/";
        String castFolder = "smeltery/casts/";
        partRecipes(consumer, ConstructsArmoryItems.HEAD_PLATE, ConstructsArmoryItems.HEAD_PLATE_CAST,
                4, partFolder, castFolder);
        partRecipes(consumer, ConstructsArmoryItems.BODY_PLATE, ConstructsArmoryItems.BODY_PLATE_CAST,
                6, partFolder, castFolder);
        partRecipes(consumer, ConstructsArmoryItems.LEGS_PLATE, ConstructsArmoryItems.LEGS_PLATE_CAST,
                5, partFolder, castFolder);
        partRecipes(consumer, ConstructsArmoryItems.FEET_PLATE, ConstructsArmoryItems.FEET_PLATE_CAST,
                3, partFolder, castFolder);
        partRecipes(consumer, ConstructsArmoryItems.MAIL, ConstructsArmoryItems.MAIL_CAST, 2,
                partFolder, castFolder);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Construct's Armory Armor Recipes";
    }

    @Nonnull
    @Override
    public String getModId() {
        return ConstructsArmoryMod.MOD_ID;
    }
}
