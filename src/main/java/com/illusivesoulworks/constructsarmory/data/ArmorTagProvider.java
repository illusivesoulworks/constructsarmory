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

import java.util.function.Consumer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryItems;

public class ArmorTagProvider extends ItemTagProvider {

  public ArmorTagProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider,
                          ExistingFileHelper existingFileHelper) {
    super(generatorIn, blockTagProvider, existingFileHelper);
  }

  @Override
  protected void addTags() {
    addArmorTags(ConstructsArmoryItems.MATERIAL_ARMOR, TinkerTags.Items.DURABILITY,
            TinkerTags.Items.MULTIPART_TOOL);
    this.tag(TinkerTags.Items.TOOL_PARTS)
            .add(ConstructsArmoryItems.HEAD_PLATE.get(), ConstructsArmoryItems.BODY_PLATE.get(),
                    ConstructsArmoryItems.LEGS_PLATE.get(), ConstructsArmoryItems.FEET_PLATE.get(),
                    ConstructsArmoryItems.MAIL.get());
    TagsProvider.TagAppender<Item> goldCasts = this.tag(TinkerTags.Items.GOLD_CASTS);
    TagsProvider.TagAppender<Item> sandCasts = this.tag(TinkerTags.Items.SAND_CASTS);
    TagsProvider.TagAppender<Item> redSandCasts = this.tag(TinkerTags.Items.RED_SAND_CASTS);
    TagsProvider.TagAppender<Item> singleUseCasts = this.tag(TinkerTags.Items.SINGLE_USE_CASTS);
    TagsProvider.TagAppender<Item> multiUseCasts = this.tag(TinkerTags.Items.MULTI_USE_CASTS);
    Consumer<CastItemObject> addCast = cast -> {
      // tag based on material
      goldCasts.add(cast.get());
      sandCasts.add(cast.getSand());
      redSandCasts.add(cast.getRedSand());
      // tag based on usage
      singleUseCasts.addTag(cast.getSingleUseTag());
      this.tag(cast.getSingleUseTag()).add(cast.getSand(), cast.getRedSand());
      multiUseCasts.addTag(cast.getMultiUseTag());
      this.tag(cast.getMultiUseTag()).add(cast.get());
    };
    addCast.accept(ConstructsArmoryItems.HEAD_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.BODY_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.LEGS_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.FEET_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.MAIL_CAST);
  }

  private TagKey<Item> getArmorTag(ArmorSlotType slotType) {
    return switch (slotType) {
      case BOOTS -> TinkerTags.Items.BOOTS;
      case LEGGINGS -> TinkerTags.Items.LEGGINGS;
      case CHESTPLATE -> TinkerTags.Items.CHESTPLATES;
      case HELMET -> TinkerTags.Items.HELMETS;
    };
  }

  @SafeVarargs
  private void addArmorTags(EnumObject<ArmorSlotType, ? extends Item> armor, TagKey<Item>... tags) {
    armor.forEach((type, item) -> {
      for (TagKey<Item> tag : tags) {
        this.tag(tag).add(item);
      }
      this.tag(getArmorTag(type)).add(item);
    });
  }
}
