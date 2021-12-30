package top.theillusivec4.constructsarmory.client;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.client.model.tools.ToolModel;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;

public class ConstructsArmoryClient {

  public static void registerColors(final ColorHandlerEvent.Item evt) {
    final ItemColors colors = evt.getItemColors();

    for (ModifiableArmorItem item : ConstructsArmoryItems.MATERIAL_ARMOR.values()) {
      ToolModel.registerItemColors(colors, () -> item);
    }
  }

  public static void setup() {
    FMLJavaModLoadingContext.get().getModEventBus()
        .addListener(ConstructsArmoryClient::textureStitch);
  }

  public static void textureStitch(final TextureStitchEvent.Pre evt) {

    if (evt.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE)) {
      evt.addSprite(new ResourceLocation(ConstructsArmoryMod.MOD_ID,
          "models/armor/material_armor_mail_layer_1"));
      evt.addSprite(new ResourceLocation(ConstructsArmoryMod.MOD_ID,
          "models/armor/material_armor_mail_layer_2"));
      evt.addSprite(new ResourceLocation(ConstructsArmoryMod.MOD_ID,
          "models/armor/material_armor_plate_layer_1"));
      evt.addSprite(new ResourceLocation(ConstructsArmoryMod.MOD_ID,
          "models/armor/material_armor_plate_layer_2"));
    }
  }
}
