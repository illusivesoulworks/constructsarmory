package top.theillusivec4.constructsarmory;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import top.theillusivec4.constructsarmory.client.ConstructsArmoryClient;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryMaterialStats;
import top.theillusivec4.constructsarmory.data.ArmorDefinitionDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorMaterialDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorMaterialStatsDataProvider;
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
    eventBus.addListener(this::colors);
    eventBus.addListener(this::gatherData);
    ConstructsArmoryItems.init();
  }

  private void setup(final FMLCommonSetupEvent evt) {
    evt.enqueueWork(ConstructsArmoryMaterialStats::setup);
  }

  private void colors(final ColorHandlerEvent.Item evt) {
    ConstructsArmoryClient.registerColors(evt);
  }

  private void gatherData(final GatherDataEvent evt) {

    if (evt.includeServer()) {
      DataGenerator generator = evt.getGenerator();
      ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
      BlockTagProvider blockTags = new BlockTagProvider(generator, existingFileHelper);
      AbstractMaterialDataProvider materials = new ArmorMaterialDataProvider(generator);
      generator.addProvider(materials);
      generator.addProvider(new ArmorMaterialStatsDataProvider(generator, materials));
      generator.addProvider(new ArmorRecipeProvider(generator));
      generator.addProvider(new ArmorDefinitionDataProvider(generator));
      generator.addProvider(new ArmorSlotLayoutProvider(generator));
      generator.addProvider(new ArmorTagProvider(generator, blockTags, existingFileHelper));
    }
  }

  public static ResourceLocation getResource(String id) {
    return new ResourceLocation(MOD_ID, id);
  }
}
