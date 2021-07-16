package top.theillusivec4.constructsarmory.common;

import net.minecraft.item.Item;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class ArmorSmeltery extends TinkerModule {

  private static final Item.Properties SMELTERY_PROPS =
      new Item.Properties().group(TinkerSmeltery.TAB_SMELTERY);

  public static final CastItemObject helmetCoreCast =
      ITEMS.registerCast("helmet_core", SMELTERY_PROPS);
  public static final CastItemObject chestplateCoreCast =
      ITEMS.registerCast("chestplate_core", SMELTERY_PROPS);
  public static final CastItemObject leggingsCoreCast =
      ITEMS.registerCast("leggings_core", SMELTERY_PROPS);
  public static final CastItemObject bootsCoreCast =
      ITEMS.registerCast("boots_core", SMELTERY_PROPS);
  public static final CastItemObject armorPlateCast =
      ITEMS.registerCast("armor_plate", SMELTERY_PROPS);
  public static final CastItemObject armorTrimCast =
      ITEMS.registerCast("armor_trim", SMELTERY_PROPS);
}
