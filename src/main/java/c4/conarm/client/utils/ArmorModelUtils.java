/*
 * Copyright (c) 2018-2019 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
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
