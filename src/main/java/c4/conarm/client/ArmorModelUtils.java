package c4.conarm.client;

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

/*This class is a re-implementation of some methods in the
ModelRegisterUtil class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class ArmorModelUtils {

    public static ResourceLocation registerArmorModel(ArmorCore armor) {
        if(armor == null || armor.getRegistryName() == null) {
            return null;
        }
        ResourceLocation itemLocation = armor.getRegistryName();
        String path = "armor/" + itemLocation.getResourcePath() + ArmorModelLoader.EXTENSION;

        ResourceLocation location = new ResourceLocation(itemLocation.getResourceDomain(), path);
        ArmorModelLoader.addPartMapping(location, armor);

        return registerArmorModel(armor, location);
    }

    public static ResourceLocation registerArmorModel(Item item, final ResourceLocation location) {
        if(!location.getResourcePath().endsWith(ArmorModelLoader.EXTENSION)) {
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
