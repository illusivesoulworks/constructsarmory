package top.theillusivec4.constructsarmory.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.data.ISafeManagerReloadListener;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.client.ArmorModelWrapper;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class MaterialArmorModel<T extends LivingEntity> extends ArmorModelWrapper<T> {

  private static final MaterialArmorModel<LivingEntity> INSTANCE = new MaterialArmorModel<>();

  private static final Map<String, RenderType> PLATE_RENDER_CACHE = new HashMap<>();
  private static final Map<String, RenderType> PLATE_LEG_RENDER_CACHE = new HashMap<>();
  private static final Map<String, RenderType> MAIL_RENDER_CACHE = new HashMap<>();
  private static final Map<String, RenderType> MAIL_LEG_RENDER_CACHE = new HashMap<>();

  private static ResourceLocation getArmorTexture(String material, String part, int variant) {
    ResourceLocation location = ResourceLocation.tryCreate(material);

    if (location == null) {
      location = MaterialIds.cobalt;
    }
    return ConstructsArmoryMod.getResource(
        String.format("textures/models/armor/material_armor_%s_layer_%d_%s_%s.png", part, variant,
            location.getNamespace(), location.getPath()));
  }

  private static final BiFunction<String, String, RenderType> ARMOR_GETTER =
      (mat, part) -> RenderType.getEntityCutoutNoCullZOffset(getArmorTexture(mat, part, 1));
  private static final BiFunction<String, String, RenderType> LEG_GETTER =
      (mat, part) -> RenderType.getEntityCutoutNoCullZOffset(getArmorTexture(mat, part, 2));

  public static final ISafeManagerReloadListener RELOAD_LISTENER = manager -> {
    PLATE_RENDER_CACHE.clear();
    PLATE_LEG_RENDER_CACHE.clear();
  };

  /**
   * Gets the model for a given entity
   */
  @SuppressWarnings("unchecked")
  public static <A extends BipedModel<?>> A getModel(ItemStack stack, EquipmentSlotType slot,
                                                     A baseModel) {
    INSTANCE.setup(stack, slot, baseModel);
    return (A) INSTANCE;
  }

  private List<String> materials = new ArrayList<>();
  private boolean isLegs = false;
  private boolean hasGlint = false;

  @Override
  public void render(@Nonnull MatrixStack matrices, @Nonnull IVertexBuilder bufferIn,
                     int packedLightIn, int packedOverlayIn, float red, float green, float blue,
                     float alpha) {

    if (base != null) {
      copyToBase();
      base.render(matrices, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

      if (!materials.isEmpty() && buffer != null) {
        IVertexBuilder overlayBuffer = ItemRenderer.getArmorVertexBuilder(buffer, isLegs ?
            PLATE_LEG_RENDER_CACHE.computeIfAbsent(materials.get(0),
                (k) -> LEG_GETTER.apply(k, "plate")) :
            PLATE_RENDER_CACHE.computeIfAbsent(materials.get(0),
                (k) -> ARMOR_GETTER.apply(k, "plate")), false, hasGlint);
        base.render(matrices, overlayBuffer, packedLightIn, packedOverlayIn, red, green, blue,
            alpha);
        overlayBuffer = ItemRenderer.getArmorVertexBuilder(buffer, isLegs ?
            MAIL_LEG_RENDER_CACHE.computeIfAbsent(materials.get(1),
                (k) -> LEG_GETTER.apply(k, "mail")) :
            MAIL_RENDER_CACHE.computeIfAbsent(materials.get(1),
                (k) -> ARMOR_GETTER.apply(k, "mail")), false, hasGlint);
        base.render(matrices, overlayBuffer, packedLightIn, packedOverlayIn, red, green, blue,
            alpha);
      }
    }
  }

  private void setup(ItemStack stack, EquipmentSlotType slot, BipedModel<?> base) {
    this.base = base;
    this.materials = new ArrayList<>();

    for (IMaterial material : ToolStack.from(stack).getMaterialsList()) {
      this.materials.add(material.getIdentifier().toString());
    }
    this.isLegs = slot == EquipmentSlotType.LEGS;
    hasGlint = stack.hasEffect();
  }
}
