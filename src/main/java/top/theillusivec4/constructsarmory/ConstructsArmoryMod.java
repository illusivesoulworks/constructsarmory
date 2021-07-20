package top.theillusivec4.constructsarmory;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.tools.data.MaterialDataProvider;
import top.theillusivec4.constructsarmory.common.ArmorItems;
import top.theillusivec4.constructsarmory.common.ArmorParts;
import top.theillusivec4.constructsarmory.common.ArmorSmeltery;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryMaterialStats;
import top.theillusivec4.constructsarmory.data.ArmorMaterialStatsDataProvider;
import top.theillusivec4.constructsarmory.data.ArmorRecipeProvider;

@Mod(ConstructsArmoryMod.MOD_ID)
public class ConstructsArmoryMod {

  public static final String MOD_ID = "constructsarmory";
  public static final Logger LOGGER = LogManager.getLogger();

  public ConstructsArmoryMod() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(EventPriority.LOW, this::setup);
    eventBus.addListener(this::gatherData);
    eventBus.register(new ArmorParts());
    eventBus.register(new ArmorItems());
    eventBus.register(new ArmorSmeltery());
  }

  private void setup(final FMLCommonSetupEvent evt) {
    evt.enqueueWork(ConstructsArmoryMaterialStats::setup);
  }

  private void gatherData(final GatherDataEvent evt) {
    if (evt.includeServer()) {
      DataGenerator generator = evt.getGenerator();
      MaterialDataProvider materials = new MaterialDataProvider(generator);
      generator.addProvider(new ArmorMaterialStatsDataProvider(generator, materials));
      generator.addProvider(new ArmorRecipeProvider(generator));
    }
  }

  public static ResourceLocation getResource(String id) {
    return new ResourceLocation(MOD_ID, id);
  }
}
