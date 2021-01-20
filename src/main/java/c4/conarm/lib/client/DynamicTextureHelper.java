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

package c4.conarm.lib.client;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.modifiers.IArmorModelModifier;
import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.UncheckedExecutionException;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DynamicTextureHelper {

    //We don't want the cache to expire since this would cause memory leak issues with the texture map the Resource Location points to
    private static Cache<CacheKey, ResourceLocation> dynamicTextureCache = CacheBuilder.newBuilder().maximumSize(1000).build();

    public static ResourceLocation getCachedTexture(ItemStack stack) {
        CacheKey key = getCacheKey(stack);
        ResourceLocation rl = null;
        if (stack.getItem() instanceof TinkersArmor) {
            try {
                rl = dynamicTextureCache.get(key, () -> getCombinedTexture(stack, ((TinkersArmor) stack.getItem())));
            } catch (ExecutionException e) {
                ConstructsArmory.logger.error("Error fetching texture from cache!");
            } catch (UncheckedExecutionException e) {
                ConstructsArmory.logger.error("Unknown error while fetching texture from cache!");
            }
        }
        return rl;
    }

    public static void refreshCache() {
        dynamicTextureCache.invalidateAll();
    }

    @Nonnull
    private static ResourceLocation getCombinedTexture(ItemStack stack, TinkersArmor armor) {

        List<BufferedImage> bufferedImages = Lists.newArrayList();
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));
        List<IModifier> modifiers = TinkerUtil.getModifiers(stack);
        int textureHeight = 0;
        int textureWidth = 0;

        for (int i = 0; i < materials.size(); i++) {

            Material material = materials.get(i);
            String identifier = material.getIdentifier();
            String partIn;

            switch (i) {
                case 0: partIn = ArmorMaterialType.CORE; break;
                case 1: partIn = ArmorMaterialType.PLATES; break;
                case 2:
                    if (materials.size() > 3) {
                        partIn = ArmorMaterialType.PLATES;
                    } else {
                        partIn = ArmorMaterialType.TRIM;
                    }
                    break;
                default: partIn = ArmorMaterialType.TRIM; break;
            }

            TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
            String loc = armor.getArmorModelTexture(stack, partIn);
            TextureAtlasSprite sprite = map.getTextureExtry(String.format("%s_%s",loc,identifier));

            if (sprite == null) {
                if (material.renderInfo.getTextureSuffix() != null) {
                    sprite = map.getTextureExtry(String.format("%s_%s",loc,material.renderInfo.getTextureSuffix()));
                    if (sprite == null) {
                        sprite = map.getTextureExtry(loc);
                    }
                } else {
                    sprite = map.getTextureExtry(loc);
                }
            }

            if (sprite == null) {
                continue;
            }

            int iconWidth = sprite.getIconWidth();
            int iconHeight = sprite.getIconHeight();

            // Tex-Fix Compatibility: https://github.com/TheIllusiveC4/ConstructsArmory/issues/207
            sprite.getFrameCount();

            int frameCount = sprite.getFrameCount();

            if (textureHeight < iconHeight) {
                textureHeight = iconHeight;
            }

            if (textureWidth < iconWidth) {
                textureWidth = iconWidth;
            }

            if (iconWidth <= 0 || iconHeight <= 0 || frameCount <= 0) {
                sprite = map.getTextureExtry(loc);

                if (sprite == null) {
                    continue;
                }
                iconWidth = sprite.getIconWidth();
                iconHeight = sprite.getIconHeight();
                sprite.getFrameCount();
                frameCount = sprite.getFrameCount();

                if (iconWidth <= 0 || iconHeight <= 0 || frameCount <= 0) {
                    continue;
                }
            }

            BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight * frameCount, BufferedImage.TYPE_4BYTE_ABGR);

            for (int j = 0; j < frameCount; j++) {
                int[][] frameTextureData = sprite.getFrameTextureData(j);
                int[] largestMipMapTextureData = frameTextureData[0];
                bufferedImage.setRGB(0, j * iconHeight, iconWidth, iconHeight, largestMipMapTextureData, 0, iconWidth);
            }

            if (material.renderInfo.useVertexColoring() && !CustomTextureCreator.exists(loc + "_" + material.identifier)) {
                int color = material.renderInfo.getVertexColor();
                int a = (color >> 24);
                if(a == 0) {
                    a = 255;
                }
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color) & 0xFF;
                float R = (float)r/255f;
                float G = (float)g/255f;
                float B = (float)b/255f;
                float A = (float)a/255f;

                for (int k = 0; k < bufferedImage.getWidth(); k++) {
                    for (int l = 0; l < bufferedImage.getHeight(); l++) {
                        int ax = bufferedImage.getColorModel().getAlpha(bufferedImage.getRaster().getDataElements(k, l, null));
                        int rx = bufferedImage.getColorModel().getRed(bufferedImage.getRaster().getDataElements(k, l, null));
                        int gx = bufferedImage.getColorModel().getGreen(bufferedImage.getRaster().getDataElements(k, l, null));
                        int bx = bufferedImage.getColorModel().getBlue(bufferedImage.getRaster().getDataElements(k, l, null));
                        rx *= R;
                        gx *= G;
                        bx *= B;
                        ax *= A;
                        bufferedImage.setRGB(k, l, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
                    }
                }
            }

            bufferedImages.add(bufferedImage);
        }

        for (IModifier modifier : modifiers) {

            TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
            String loc;
            if (modifier instanceof IArmorMaterialTexture) {
                loc = ((IArmorMaterialTexture) modifier).getBaseTexture();
            } else if (modifier instanceof IArmorModelModifier){
                loc = String.format("%s_%s", ((IArmorModelModifier) modifier).getModelTextureLocation(), modifier.getIdentifier());
            } else {
                continue;
            }
            TextureAtlasSprite sprite = map.getAtlasSprite(loc);

            if (sprite == map.getMissingSprite()) {
                continue;
            }

            int iconWidth = sprite.getIconWidth();
            int iconHeight = sprite.getIconHeight();
            int frameCount = sprite.getFrameCount();

            if (iconWidth <= 0 || iconHeight <= 0 || frameCount <= 0) {
                continue;
            }

            BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight * frameCount, BufferedImage.TYPE_4BYTE_ABGR);

            for (int k = 0; k < frameCount; k++) {
                int[][] frameTextureData = sprite.getFrameTextureData(k);
                int[] largestMipMapTextureData = frameTextureData[0];
                bufferedImage.setRGB(0, k * iconHeight, iconWidth, iconHeight, largestMipMapTextureData, 0, iconWidth);
            }

            bufferedImages.add(bufferedImage);
        }

        BufferedImage combined = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = combined.createGraphics();

        for (BufferedImage img : bufferedImages) {
            g.drawImage(img, 0, 0, null);
        }

        g.dispose();
        return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("constructsarmor", new DynamicTexture(combined));
    }

    public static CacheKey getCacheKey(ItemStack stack) {
        return new CacheKey(stack);
    }

    public static class CacheKey {

        final NBTTagCompound data;

        public CacheKey(ItemStack stack) {
            this.data = TagUtil.getTagSafe(stack);
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }
            if(o == null || getClass() != o.getClass()) {
                return false;
            }

            CacheKey cacheKey = (CacheKey) o;

            return data != null ? data.equals(cacheKey.data) : cacheKey.data == null;

        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }
    }
}
