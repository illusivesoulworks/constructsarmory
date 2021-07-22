package top.theillusivec4.constructsarmory.client;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import slimeknights.tconstruct.tools.ToolClientEvents;
import top.theillusivec4.constructsarmory.common.ArmorItems;
import top.theillusivec4.constructsarmory.common.ArmorParts;

public class ConstructsArmoryClient {

  public static void registerColors(final ColorHandlerEvent.Item evt) {
    final ItemColors colors = evt.getItemColors();
    ToolClientEvents.registerToolItemColors(colors, ArmorItems.helmet);
    ToolClientEvents.registerToolItemColors(colors, ArmorItems.chestplate);
    ToolClientEvents.registerToolItemColors(colors, ArmorItems.leggings);
    ToolClientEvents.registerToolItemColors(colors, ArmorItems.boots);
    ToolClientEvents.registerMaterialItemColors(colors, ArmorParts.armorPlate);
    ToolClientEvents.registerMaterialItemColors(colors, ArmorParts.armorTrim);
    ToolClientEvents.registerMaterialItemColors(colors, ArmorParts.helmetCore);
    ToolClientEvents.registerMaterialItemColors(colors, ArmorParts.chestplateCore);
    ToolClientEvents.registerMaterialItemColors(colors, ArmorParts.leggingsCore);
    ToolClientEvents.registerMaterialItemColors(colors, ArmorParts.bootsCore);
  }
}
