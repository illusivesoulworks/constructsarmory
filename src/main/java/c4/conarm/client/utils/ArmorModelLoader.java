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
import c4.conarm.lib.client.DynamicTextureHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.io.FilenameUtils;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.client.model.*;
import slimeknights.tconstruct.library.client.model.format.AmmoPosition;
import slimeknights.tconstruct.library.client.model.format.ToolModelOverride;
import slimeknights.tconstruct.library.tools.IToolPart;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ArmorModelLoader implements ICustomModelLoader {

    public static String EXTENSION = ".conarm";

    private static final Map<ResourceLocation, ArmorCore> modelItemMap = Maps.newHashMap();

    public static void addPartMapping(ResourceLocation resourceLocation, ArmorCore armor) {
        modelItemMap.put(resourceLocation, armor);
    }

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getPath().endsWith(EXTENSION);
    }

    @Override
    @Nonnull
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) {
        try {
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);
            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.loadTransformFromJson(modelLocation);
            ImmutableList<ToolModelOverride> overrides = ModelHelper.loadToolModelOverridesFromJson(modelLocation);
            AmmoPosition ammoPosition = ModelHelper.loadAmmoPositionFromJson(modelLocation);
            Float[] rotations = ModelHelper.loadLayerRotations(modelLocation);

            if(rotations.length > 0 && textures.size() != rotations.length) {
                ConstructsArmory.logger.error("Armormodel {} has invalid layerrotation entry: Size should be {} but is {}; Skipping rotations.", modelLocation, textures.size(), rotations.length);
                rotations = new Float[0];
            }

            ImmutableList.Builder<ResourceLocation> defaultTextureListBuilder = ImmutableList.builder();
            List<MaterialModel> parts = Lists.newArrayList();
            List<MaterialModel> brokenParts = Lists.newArrayList();

            ArmorCore armorCore = modelItemMap.get(MaterialModelLoader.getReducedPath(modelLocation));

            for(Map.Entry<String, String> entry : textures.entrySet()) {
                String name = entry.getKey();
                try {
                    int i;
                    List<MaterialModel> listToAdd;

                    if(name.startsWith("layer")) {
                        i = Integer.valueOf(name.substring(5));
                        listToAdd = parts;
                    }
                    else if(name.startsWith("broken")) {
                        i = Integer.valueOf(name.substring(6));
                        listToAdd = brokenParts;
                    }
                    else {
                        ConstructsArmory.logger.warn("Armormodel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                        continue;
                    }

                    ResourceLocation location = new ResourceLocation(entry.getValue());
                    MaterialModel partModel = new MaterialModel(ImmutableList.of(location));
                    while(listToAdd.size() <= i) {
                        listToAdd.add(null);
                    }
                    listToAdd.set(i, partModel);

                    defaultTextureListBuilder.add(location);
                    registerCustomTextures(i, location, armorCore);
                } catch(NumberFormatException e) {
                    ConstructsArmory.logger.error("Armormodel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                }
            }

            for(ToolModelOverride override : overrides) {
                for(Map.Entry<String, String> entry : override.textures.entrySet()) {
                    String name = entry.getKey();
                    try {
                        int i;
                        TIntObjectHashMap<MaterialModel> mapToAdd;

                        if(name.startsWith("layer")) {
                            i = Integer.valueOf(name.substring(5));
                            mapToAdd = override.partModelReplacement;
                        }
                        else if(name.startsWith("broken")) {
                            i = Integer.valueOf(name.substring(6));
                            mapToAdd = override.brokenPartModelReplacement;
                        }
                        else {
                            ConstructsArmory.logger.warn("Armormodel {} has invalid texture override entry {}; Skipping layer.", modelLocation, name);
                            continue;
                        }

                        ResourceLocation location = new ResourceLocation(entry.getValue());
                        MaterialModel partModel = new MaterialModel(ImmutableList.of(location));
                        mapToAdd.put(i, partModel);

                        registerCustomTextures(i, location, armorCore);
                    } catch(NumberFormatException e) {
                        ConstructsArmory.logger.error("Armormodel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                    }
                }
            }

            String toolName = FilenameUtils.getBaseName(modelLocation.getPath());
            IModel mods;
            ModifierModel modifiers = null;
            try {
                mods = ModelLoaderRegistry.getModel(ModifierModelLoader.getLocationForToolModifiers(modelLocation.getNamespace(),
                        toolName));

                if(mods == null || !(mods instanceof ModifierModel)) {
                    ConstructsArmory.logger.trace(
                            "Armormodel {} does not have any modifiers associated with it. Be sure that the Armors internal name, the Armormodels filename and the name used inside the Modifier Model Definition match!",
                            modelLocation);
                }
                else {
                    modifiers = (ModifierModel) mods;

                    for(ToolModelOverride toolModelOverride : overrides) {
                        if(toolModelOverride.modifierSuffix != null) {
                            String modifierName = toolName + toolModelOverride.modifierSuffix;
                            IModel extraModel = ModelLoaderRegistry.getModel(
                                    ModifierModelLoader.getLocationForToolModifiers(modelLocation.getNamespace(), modifierName));
                            if(extraModel instanceof ModifierModel) {
                                ModifierModel overriddenModifierModel = new ModifierModel();
                                for(Map.Entry<String, String> entry : modifiers.getModels().entrySet()) {
                                    overriddenModifierModel.addModelForModifier(entry.getKey(), entry.getValue());
                                }
                                for(Map.Entry<String, String> entry : ((ModifierModel) extraModel).getModels().entrySet()) {
                                    overriddenModifierModel.addModelForModifier(entry.getKey(), entry.getValue());
                                }
                                toolModelOverride.overrideModifierModel = overriddenModifierModel;
                            }
                        }
                    }
                }
            } catch(Exception e) {
                ConstructsArmory.logger.error(e);
                modifiers = null;
            }

            return new ToolModel(defaultTextureListBuilder.build(), parts, brokenParts, rotations, modifiers, transforms, overrides, ammoPosition);
        } catch(IOException e) {
            ConstructsArmory.logger.error("Could not load multimodel {}", modelLocation.toString());
        }
        return ModelLoaderRegistry.getMissingModel();
    }

    private void registerCustomTextures(int i, ResourceLocation resourceLocation, ArmorCore armorCore) {
        if(armorCore == null) {
            CustomTextureCreator.registerTexture(resourceLocation);
        }
        else {
            for(IToolPart part : armorCore.getRequiredComponents().get(i).getPossibleParts()) {
                CustomTextureCreator.registerTextureForPart(resourceLocation, part);
            }
        }
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        DynamicTextureHelper.refreshCache();
    }
}
