package top.theillusivec4.constructsarmory.client;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import slimeknights.tconstruct.library.client.model.tools.ToolModel;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;

public class ConstructsArmoryClient {

  public static void registerColors(final ColorHandlerEvent.Item evt) {
    final ItemColors colors = evt.getItemColors();

    for (ModifiableArmorItem item : ConstructsArmoryItems.MATERIAL_ARMOR.values()) {
      ToolModel.registerItemColors(colors, () -> item);
    }
  }
}
