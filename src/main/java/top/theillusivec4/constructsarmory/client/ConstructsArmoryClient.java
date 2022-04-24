package top.theillusivec4.constructsarmory.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
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

  public static void setup() {
    Minecraft minecraft = Minecraft.getInstance();
    //noinspection ConstantConditions
    if (minecraft != null) {
      IResourceManager manager = Minecraft.getInstance().getResourceManager();

      if (manager instanceof IReloadableResourceManager) {
        ((IReloadableResourceManager) manager).addReloadListener(
            MaterialArmorModel.RELOAD_LISTENER);
      }
    }
  }
}
