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

package c4.conarm.client.utils;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.armor.ArmorCore;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import slimeknights.tconstruct.common.ModelRegisterUtil;

import javax.annotation.Nonnull;

public class ArmorModelUtils {

    public static ResourceLocation registerArmorModel(ArmorCore armor) {
        if(armor == null || armor.getRegistryName() == null) {
            return null;
        }
        ResourceLocation itemLocation = armor.getRegistryName();
        String path = "armor/" + itemLocation.getPath() + ArmorModelLoader.EXTENSION;

        ResourceLocation location = new ResourceLocation(itemLocation.getNamespace(), path);
        ArmorModelLoader.addPartMapping(location, armor);

        return registerArmorModel(armor, location);
    }

    public static ResourceLocation registerArmorModel(Item item, final ResourceLocation location) {
        if(!location.getPath().endsWith(ArmorModelLoader.EXTENSION)) {
            ConstructsArmory.logger.error("The material-model " + location.toString() + " does not end with '"
                    + ArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItem(item, location);
    }

    private static ResourceLocation registerItem(Item item, final ResourceLocation location) {
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
                return new ModelResourceLocation(location, ModelRegisterUtil.VARIANT_INVENTORY);
            }
        });

        ModelLoader.registerItemVariants(item, location);

        return location;
    }
}
