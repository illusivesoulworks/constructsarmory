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

package com.illusivesoulworks.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.api.ArmorMaterialStatsIdentifiers;

public class ArmorPartSpriteProvider extends AbstractPartSpriteProvider {

  public ArmorPartSpriteProvider() {
    super(ConstructsArmoryMod.MOD_ID);
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Part Textures";
  }

  @Override
  protected void addAllSpites() {
    addPlate("body_plate");
    addPlate("head_plate");
    addPlate("legs_plate");
    addPlate("feet_plate");
    addPart("mail", ArmorMaterialStatsIdentifiers.MAIL);

    buildArmor("helmet").addBreakablePart("head_plate", ArmorMaterialStatsIdentifiers.PLATE)
        .addPart("mail", ArmorMaterialStatsIdentifiers.MAIL);
    buildArmor("chestplate").addBreakablePart("body_plate", ArmorMaterialStatsIdentifiers.PLATE)
        .addPart("mail", ArmorMaterialStatsIdentifiers.MAIL);
    buildArmor("leggings").addBreakablePart("legs_plate", ArmorMaterialStatsIdentifiers.PLATE)
        .addPart("mail", ArmorMaterialStatsIdentifiers.MAIL);
    buildArmor("boots").addBreakablePart("feet_plate", ArmorMaterialStatsIdentifiers.PLATE)
        .addPart("mail", ArmorMaterialStatsIdentifiers.MAIL);

    addTexture("models/armor/material_armor_plate_layer_1", ArmorMaterialStatsIdentifiers.PLATE);
    addTexture("models/armor/material_armor_plate_layer_2", ArmorMaterialStatsIdentifiers.PLATE);
    addTexture("models/armor/material_armor_mail_layer_1", ArmorMaterialStatsIdentifiers.MAIL);
    addTexture("models/armor/material_armor_mail_layer_2", ArmorMaterialStatsIdentifiers.MAIL);
  }

  protected void addPlate(String name) {
    addPart(name, ArmorMaterialStatsIdentifiers.PLATE);
  }

  protected ToolSpriteBuilder buildArmor(String name) {
    return buildTool(new ResourceLocation(ConstructsArmoryMod.MOD_ID, name));
  }
}
