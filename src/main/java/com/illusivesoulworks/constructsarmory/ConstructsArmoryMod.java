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

package com.illusivesoulworks.constructsarmory;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import com.illusivesoulworks.constructsarmory.client.ConstructsArmoryClient;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryEffects;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryEvents;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryItems;
import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryModifiers;
import com.illusivesoulworks.constructsarmory.common.stat.ConstructsArmoryMaterialStats;
import com.illusivesoulworks.constructsarmory.common.stat.ConstructsArmoryStats;
import com.illusivesoulworks.constructsarmory.data.ArmorDefinitionDataProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorMaterialDataProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorMaterialSpriteProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorMaterialStatsDataProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorMaterialTraitsDataProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorPartSpriteProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorRecipeProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorSlotLayoutProvider;
import com.illusivesoulworks.constructsarmory.data.ArmorTagProvider;

@Mod(ConstructsArmoryMod.MOD_ID)
public class ConstructsArmoryMod {

  public static final String MOD_ID = "constructsarmory";
  public static final Logger LOGGER = LogManager.getLogger();

  public ConstructsArmoryMod() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
    eventBus.addListener(this::gatherData);
    ConstructsArmoryItems.init();
    ConstructsArmoryModifiers.init();
    ConstructsArmoryStats.init();
    ConstructsArmoryEffects.init();
    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ConstructsArmoryClient::init);
  }

  private void setup(final FMLCommonSetupEvent evt) {
    ConstructsArmoryEvents.setup();
    evt.enqueueWork(ConstructsArmoryMaterialStats::setup);
  }

  private void gatherData(final GatherDataEvent evt) {
    ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
    DataGenerator generator = evt.getGenerator();

    if (evt.includeServer()) {
      BlockTagProvider blockTags = new BlockTagProvider(generator, existingFileHelper);
      AbstractMaterialDataProvider materials = new ArmorMaterialDataProvider(generator);
      generator.addProvider(materials);
      generator.addProvider(new ArmorMaterialStatsDataProvider(generator, materials));
      generator.addProvider(new ArmorMaterialTraitsDataProvider(generator, materials));
      generator.addProvider(new ArmorRecipeProvider(generator));
      generator.addProvider(new ArmorDefinitionDataProvider(generator));
      generator.addProvider(new ArmorSlotLayoutProvider(generator));
      generator.addProvider(new ArmorTagProvider(generator, blockTags, existingFileHelper));
    }

    if (evt.includeClient()) {
      ArmorPartSpriteProvider armorPartSpriteProvider = new ArmorPartSpriteProvider();
      generator.addProvider(
          new GeneratorPartTextureJsonGenerator(generator, ConstructsArmoryMod.MOD_ID,
              armorPartSpriteProvider));
      generator.addProvider(
          new MaterialPartTextureGenerator(generator, existingFileHelper, armorPartSpriteProvider,
              new ArmorMaterialSpriteProvider()));
    }
  }

  public static ResourceLocation getResource(String id) {
    return new ResourceLocation(MOD_ID, id);
  }

  public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
    return TinkerDataCapability.TinkerDataKey.of(getResource(name));
  }
}
