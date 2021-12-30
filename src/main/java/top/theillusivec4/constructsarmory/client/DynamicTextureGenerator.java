package top.theillusivec4.constructsarmory.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      int materialColor = MaterialRenderInfoLoader.INSTANCE.getRenderInfo(identifier)
          .map(renderInfo -> renderInfo.getSprite(renderMaterial, RenderMaterial::getSprite)
              .getColor())
          .orElse(0);
      int a = (materialColor >> 24) & 0xFF;
      int r = (materialColor >> 16) & 0xFF;
      int g = (materialColor >> 8) & 0xFF;
      int b = (materialColor) & 0xFF;

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

          if (i == 0 || ax > 0.0f) {
            nativeImage.setPixelRGBA(j, k, (ax << 24) | (bx << 16) | (gx << 8) | (rx));
          }
        }
      }
    }
    return Minecraft.getInstance().getTextureManager()
        .getDynamicTextureLocation(ConstructsArmoryMod.MOD_ID, new DynamicTexture(nativeImage));
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
