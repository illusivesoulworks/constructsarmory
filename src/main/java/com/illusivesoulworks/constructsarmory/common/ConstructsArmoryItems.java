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

package com.illusivesoulworks.constructsarmory.common;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import com.illusivesoulworks.constructsarmory.common.item.MaterialArmorItem;

public class ConstructsArmoryItems {

  private static final ItemDeferredRegisterExtension
      ITEMS = new ItemDeferredRegisterExtension(ConstructsArmoryMod.MOD_ID);

  // Armor
  private static final Supplier<Item.Properties> ARMOR =
      () -> new Item.Properties().tab(TinkerTools.TAB_TOOLS);

  public static final EnumObject<ArmorSlotType, ModifiableArmorItem> MATERIAL_ARMOR =
      ITEMS.registerEnum("material_armor", ArmorSlotType.values(),
          type -> new MaterialArmorItem(ConstructsArmoryDefinitions.MATERIAL_ARMOR, type,
              ARMOR.get()));

  // Armor Parts
  private static final Item.Properties PARTS_PROPS =
      new Item.Properties().tab(TinkerToolParts.TAB_TOOL_PARTS);

  public static final ItemObject<ToolPartItem> HEAD_PLATE =
      ITEMS.register("head_plate", () -> new ToolPartItem(PARTS_PROPS,
          ArmorMaterialStatsIdentifiers.PLATE));
  public static final ItemObject<ToolPartItem> BODY_PLATE =
      ITEMS.register("body_plate", () -> new ToolPartItem(PARTS_PROPS,
          ArmorMaterialStatsIdentifiers.PLATE));
  public static final ItemObject<ToolPartItem> LEGS_PLATE =
      ITEMS.register("legs_plate", () -> new ToolPartItem(PARTS_PROPS,
          ArmorMaterialStatsIdentifiers.PLATE));
  public static final ItemObject<ToolPartItem> FEET_PLATE =
      ITEMS.register("feet_plate", () -> new ToolPartItem(PARTS_PROPS,
          ArmorMaterialStatsIdentifiers.PLATE));
  public static final ItemObject<ToolPartItem> MAIL = ITEMS.register("mail",
      () -> new ToolPartItem(PARTS_PROPS, ArmorMaterialStatsIdentifiers.MAIL));

  // Smeltery
  private static final Item.Properties SMELTERY_PROPS =
      new Item.Properties().tab(TinkerSmeltery.TAB_SMELTERY);

  public static final CastItemObject HEAD_PLATE_CAST =
      ITEMS.registerCast("head_plate", SMELTERY_PROPS);
  public static final CastItemObject BODY_PLATE_CAST =
      ITEMS.registerCast("body_plate", SMELTERY_PROPS);
  public static final CastItemObject LEGS_PLATE_CAST =
      ITEMS.registerCast("legs_plate", SMELTERY_PROPS);
  public static final CastItemObject FEET_PLATE_CAST =
      ITEMS.registerCast("feet_plate", SMELTERY_PROPS);
  public static final CastItemObject MAIL_CAST =
      ITEMS.registerCast("mail", SMELTERY_PROPS);

  public static void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    ITEMS.register(bus);
  }
}
