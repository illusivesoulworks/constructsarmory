package top.theillusivec4.constructsarmory;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
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
import top.theillusivec4.constructsarmory.client.ConstructsArmoryClient;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryModifiers;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryStats;
import top.theillusivec4.constructsarmory.data.ArmorDefinitionDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorMaterialDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorMaterialSpriteProvider;
import top.theillusivec4.constructsarmory.data.ArmorMaterialStatsDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorMaterialTraitsDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorPartSpriteProvider;
import top.theillusivec4.constructsarmory.data.ArmorRecipeProvider;
import top.theillusivec4.constructsarmory.data.ArmorSlotLayoutProvider;
import top.theillusivec4.constructsarmory.data.ArmorTagProvider;

@Mod(ConstructsArmoryMod.MOD_ID)
public class ConstructsArmoryMod {

  public static final String MOD_ID = "constructsarmory";
  public static final Logger LOGGER = LogManager.getLogger();

  public ConstructsArmoryMod() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
    eventBus.addListener(this::clientSetup);
    eventBus.addListener(this::colors);
    eventBus.addListener(this::gatherData);
    ConstructsArmoryItems.init();
    ConstructsArmoryModifiers.init();
    ConstructsArmoryStats.init();
  }

  private void setup(final FMLCommonSetupEvent evt) {
    evt.enqueueWork(ConstructsArmoryMaterialStats::setup);
  }

  private void clientSetup(final FMLClientSetupEvent evt) {
    ConstructsArmoryClient.setup();
  }

  private void colors(final ColorHandlerEvent.Item evt) {
    ConstructsArmoryClient.registerColors(evt);
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
