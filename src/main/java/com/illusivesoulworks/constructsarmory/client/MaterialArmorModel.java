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

package com.illusivesoulworks.constructsarmory.client;

import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lombok.SneakyThrows;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.ISafeManagerReloadListener;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.nbt.MaterialIdNBT;
import slimeknights.tconstruct.tools.client.ArmorModelHelper;
import slimeknights.tconstruct.tools.client.PlateArmorModel;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Modified copy of {@link PlateArmorModel} from Tinkers' Construct
 * MIT License (c) SlimeKnights
 */
public class MaterialArmorModel extends Model {

  private static final MaterialArmorModel INSTANCE = new MaterialArmorModel();

  private static final Map<String, RenderType> PLATE_RENDER_CACHE = new HashMap<>();
  private static final Map<String, RenderType> PLATE_LEG_RENDER_CACHE = new HashMap<>();
  private static final Map<String, RenderType> MAIL_RENDER_CACHE = new HashMap<>();
  private static final Map<String, RenderType> MAIL_LEG_RENDER_CACHE = new HashMap<>();

  public MaterialArmorModel() {
    super(RenderType::entityCutoutNoCull);
  }

  private static ResourceLocation getArmorTexture(String material, String part, int variant) {
    MaterialVariantId variantId = MaterialVariantId.tryParse(material);

    if (variantId == null) {
      System.out.println("Invalid material: " + material);
      variantId = MaterialIds.cobalt;
    }
    ResourceLocation location = variantId.getLocation('_');
    return ConstructsArmoryMod.getResource(
            String.format("textures/models/armor/material_armor_%s_layer_%d_%s_%s.png", part, variant,
                    location.getNamespace(), location.getPath()));
  }

  private static final BiFunction<String, String, RenderType> ARMOR_GETTER =
      (mat, part) -> RenderType.entityCutoutNoCullZOffset(getArmorTexture(mat, part, 1));
  private static final BiFunction<String, String, RenderType> LEG_GETTER =
      (mat, part) -> RenderType.entityCutoutNoCullZOffset(getArmorTexture(mat, part, 2));

  public static final ISafeManagerReloadListener RELOAD_LISTENER = manager -> {
    PLATE_RENDER_CACHE.clear();
    PLATE_LEG_RENDER_CACHE.clear();
  };

  /**
   * Gets the model for a given entity
   */
  public static Model getModel(ItemStack stack, EquipmentSlot slot,
                                          HumanoidModel<?> baseModel) {
    INSTANCE.setup(stack, slot, baseModel);
    return INSTANCE;
  }

  @Nullable
  private HumanoidModel<?> base;

  private List<String> materials = new ArrayList<>();
  private boolean isLegs = false;
  private boolean hasGlint = false;

  @SneakyThrows
  @Override
  public void renderToBuffer(@NotNull PoseStack matrices, @NotNull VertexConsumer buffer, int packedLightIn,
                             int packedOverlayIn, float red, float green,
                             float blue, float alpha) {
    if (base != null) {
      base.renderToBuffer(matrices, buffer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      if (!materials.isEmpty()) {
        MultiBufferSource mbf = (MultiBufferSource) FieldUtils.getDeclaredField(ArmorModelHelper.class, "buffer", true).get(null);
        VertexConsumer overlayBuffer = ItemRenderer.getArmorFoilBuffer(mbf, isLegs ?
                PLATE_LEG_RENDER_CACHE.computeIfAbsent(materials.get(0),
                        (k) -> LEG_GETTER.apply(k, "plate")) :
                PLATE_RENDER_CACHE.computeIfAbsent(materials.get(0),
                        (k) -> ARMOR_GETTER.apply(k, "plate")), false, hasGlint);
        base.renderToBuffer(matrices, overlayBuffer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        overlayBuffer = ItemRenderer.getArmorFoilBuffer(mbf, isLegs ?
                MAIL_LEG_RENDER_CACHE.computeIfAbsent(materials.get(1),
                        (k) -> LEG_GETTER.apply(k, "mail")) :
                MAIL_RENDER_CACHE.computeIfAbsent(materials.get(1),
                        (k) -> ARMOR_GETTER.apply(k, "mail")), false, hasGlint);
        base.renderToBuffer(matrices, overlayBuffer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      }
    }
  }

  private void setup(ItemStack stack, EquipmentSlot slot, HumanoidModel<?> base) {
    this.base = base;
    this.materials = MaterialIdNBT.from(stack).getMaterials()
            .stream().map(Object::toString).toList();
    this.isLegs = slot == EquipmentSlot.LEGS;
    hasGlint = stack.hasFoil();
  }
}
