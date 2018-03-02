package c4.conarm.lib.client;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.proxy.ClientProxy;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DynamicTextureHelper {

    //We don't want the cache to expire since this would cause memory leak issues with the texture map the Resource Location points to
    private static Cache<CacheKey, ResourceLocation> dynamicTextureCache = CacheBuilder.newBuilder().maximumSize(1000).build();

    public static ResourceLocation getCachedTexture(ItemStack stack) {
        CacheKey key = getCacheKey(stack);
        ResourceLocation rl = null;
        try {
            rl = dynamicTextureCache.get(key, () -> getCombinedTexture(stack));
        } catch (ExecutionException e) {
            //NO-OP
        }
        return rl;
    }

    private static ResourceLocation getCombinedTexture(ItemStack stack) {

        List<BufferedImage> bufferedImages = Lists.newArrayList();
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));

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
            String loc = String.format("%s_%s","conarm:models/armor/armor",partIn);
            TextureAtlasSprite sprite = map.getTextureExtry(String.format("%s_%s",loc,identifier));

            if (sprite == null) {
                sprite = map.getTextureExtry(loc);
            }

            if (sprite == null) {
                continue;
            }

            int iconWidth = sprite.getIconWidth();
            int iconHeight = sprite.getIconHeight();
            int frameCount = sprite.getFrameCount();

            if (iconWidth <= 0 || iconHeight <= 0 || frameCount <= 0) {
                return null;
            }

            BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight * frameCount, BufferedImage.TYPE_4BYTE_ABGR);

            for (int j = 0; j < frameCount; j++) {
                int[][] frameTextureData = sprite.getFrameTextureData(j);
                int[] largestMipMapTextureData = frameTextureData[0];
                bufferedImage.setRGB(0, j * iconHeight, iconWidth, iconHeight, largestMipMapTextureData, 0, iconWidth);
            }

            if (material.renderInfo.useVertexColoring()) {
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

        BufferedImage combined = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = combined.createGraphics();

        for (BufferedImage img : bufferedImages) {
            g.drawImage(img, 0, 0, null);
        }

        g.dispose();
//        try {
//            ImageIO.write(combined, "png", new File("dump.png"));
//        } catch (IOException e) {
//            ConstructsArmory.logger.warn("Unable to write image!");
//        }
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
