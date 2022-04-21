package top.theillusivec4.constructsarmory.client;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nonnull;
import lombok.Data;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.materials.MaterialRenderInfoLoader;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.MaterialIdNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.item.MaterialArmorItem;

public class DynamicTextureGenerator {

  // The textures do not get discarded at any point, so we just maintain the reference
  private static final Map<ArmorCacheKey, ResourceLocation> DYNAMIC_TEXTURES = new HashMap<>();

  public static String getCachedTexture(ItemStack stack, boolean isLegs) {
    ResourceLocation rl = null;

    if (stack.getItem() instanceof MaterialArmorItem) {
      rl = DYNAMIC_TEXTURES.computeIfAbsent(getCacheKey(stack, isLegs),
          (k) -> getCombinedTexture(stack, isLegs));
    }
    return rl != null ? rl.toString() : null;
  }

  @Nonnull
  private static ResourceLocation getCombinedTexture(ItemStack stack, boolean isLegs) {
    List<IMaterial> materials = ToolStack.from(stack).getMaterialsList();
    NativeImage nativeImage = new NativeImage(64, 32, false);

    for (int i = 0; i < materials.size(); i++) {
      MaterialId identifier = materials.get(i).getIdentifier();
      String part = i == 0 ? "plate" : "mail";
      String index = isLegs ? "2" : "1";
      String name = "models/armor/material_armor_" + part + "_layer_" + index;
      ResourceLocation rl = new ResourceLocation(ConstructsArmoryMod.MOD_ID, name);
      RenderMaterial renderMaterial =
          new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, rl);
      TextureAtlasSprite sprite = renderMaterial.getSprite();
      int width = sprite.getWidth();
      int height = sprite.getHeight();
      AtomicInteger light = new AtomicInteger();
      AtomicInteger materialColor = new AtomicInteger();
      MaterialRenderInfoLoader.INSTANCE.getRenderInfo(identifier).ifPresent(renderInfo -> {
        materialColor.set(
            renderInfo.getSprite(renderMaterial, RenderMaterial::getSprite).getColor());
        light.set(renderInfo.getLuminosity());
      });
      int a = (materialColor.get() >> 24) & 0xFF;
      int r = (materialColor.get() >> 16) & 0xFF;
      int g = (materialColor.get() >> 8) & 0xFF;
      int b = (materialColor.get()) & 0xFF;

      if (a == 0) {
        a = 255;
      }
      float R = (float) r / 255.0f;
      float G = (float) g / 255.0f;
      float B = (float) b / 255.0f;
      float A = (float) a / 255.0f;

      for (int j = 0; j < width; j++) {

        for (int k = 0; k < height; k++) {
          int rgba = sprite.getPixelRGBA(0, j, k);
          int ax = (rgba >> 24) & 0xFF;
          int rx = (rgba >> 16) & 0xFF;
          int gx = (rgba >> 8) & 0xFF;
          int bx = (rgba) & 0xFF;
          rx *= R;
          gx *= G;
          bx *= B;
          ax *= A;
          float[] hsb = Color.RGBtoHSB(rx, gx, bx, null);
          hsb[2] = Math.min(1.0f, hsb[2] * 1.25f);
          int newRgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
          rx = (newRgb >> 16) & 0xFF;
          gx = (newRgb >> 8) & 0xFF;
          bx = (newRgb) & 0xFF;

          if (i == 0 || ax > 0.0f) {
            nativeImage.setPixelRGBA(j, k, (ax << 24) | (bx << 16) | (gx << 8) | (rx));
          }
        }
      }
    }
    return Minecraft.getInstance().getTextureManager()
        .getDynamicTextureLocation(ConstructsArmoryMod.MOD_ID, new DynamicTexture(nativeImage));
  }

  private static float[] brightenRgb(float r, float g, float b, float increase) {
    float cmax = Math.max(r, Math.max(g, b));
    float cmin = Math.min(r, Math.min(g, b));
    float diff = cmax - cmin;
    float h = -1, s;

    if (cmax == cmin) {
      h = 0;
    } else if (cmax == r) {
      h = (60 * ((g - b) / diff) + 360) % 360;
    } else if (cmax == g) {
      h = (60 * ((b - r) / diff) + 120) % 360;
    } else if (cmax == b) {
      h = (60 * ((r - g) / diff) + 240) % 360;
    }

    if (cmax == 0) {
      s = 0;
    } else {
      s = (diff / cmax) * 100;
    }
    float v = cmax * 100 * increase;

    if (s == 0.0F) {
      r = g = b = (int)(v * 255.0F + 0.5F);
    } else {
      float h1 = (h - (float)Math.floor(h)) * 6.0F;
      float f = h1 - (float)Math.floor(h1);
      float p = v * (1.0F - s);
      float q = v * (1.0F - s * f);
      float t = v * (1.0F - s * (1.0F - f));

      switch((int)h1) {
        case 0:
          r = (int)(v * 255.0F + 0.5F);
          g = (int)(t * 255.0F + 0.5F);
          b = (int)(p * 255.0F + 0.5F);
          break;
        case 1:
          r = (int)(q * 255.0F + 0.5F);
          g = (int)(v * 255.0F + 0.5F);
          b = (int)(p * 255.0F + 0.5F);
          break;
        case 2:
          r = (int)(p * 255.0F + 0.5F);
          g = (int)(v * 255.0F + 0.5F);
          b = (int)(t * 255.0F + 0.5F);
          break;
        case 3:
          r = (int)(p * 255.0F + 0.5F);
          g = (int)(q * 255.0F + 0.5F);
          b = (int)(v * 255.0F + 0.5F);
          break;
        case 4:
          r = (int)(t * 255.0F + 0.5F);
          g = (int)(p * 255.0F + 0.5F);
          b = (int)(v * 255.0F + 0.5F);
          break;
        case 5:
          r = (int)(v * 255.0F + 0.5F);
          g = (int)(p * 255.0F + 0.5F);
          b = (int)(q * 255.0F + 0.5F);
      }
    }
    return new float[]{r, g, b};
  }

  public static ArmorCacheKey getCacheKey(ItemStack stack, boolean isLegs) {
    List<MaterialId> materials = MaterialIdNBT.from(stack).getMaterials();
    boolean broken = ToolDamageUtil.isBroken(stack);
    return new ArmorCacheKey(materials, broken, isLegs);
  }

  @Data
  private static class ArmorCacheKey {
    private final List<MaterialId> materials;
    private final boolean broken;
    private final boolean isLegs;
  }
}
