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

package com.illusivesoulworks.constructsarmory.client;

import com.illusivesoulworks.constructsarmory.common.ConstructsArmoryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.client.model.tools.ToolModel;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;

public class ConstructsArmoryClient {

  public static void init() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(ConstructsArmoryClient::setup);
    eventBus.addListener(ConstructsArmoryClient::colors);
  }

  public static void colors(final ColorHandlerEvent.Item evt) {
    final ItemColors colors = evt.getItemColors();

    for (ModifiableArmorItem item : ConstructsArmoryItems.MATERIAL_ARMOR.values()) {
      ToolModel.registerItemColors(colors, () -> item);
    }
  }

  public static void setup(final FMLClientSetupEvent evt) {
    Minecraft minecraft = Minecraft.getInstance();
    //noinspection ConstantConditions
    if (minecraft != null) {
      ResourceManager rm = Minecraft.getInstance().getResourceManager();

      if (rm instanceof ReloadableResourceManager resource) {
        resource.registerReloadListener(
            MaterialArmorModel.RELOAD_LISTENER);
      }
    }
  }
}
