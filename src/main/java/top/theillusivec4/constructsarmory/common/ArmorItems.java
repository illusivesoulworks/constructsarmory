package top.theillusivec4.constructsarmory.common;

import java.util.function.Supplier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.mantle.util.SupplierItemGroup;
import slimeknights.tconstruct.common.TinkerModule;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public final class ArmorItems extends TinkerModule {

  public static final ItemGroup TAB_ARMOR =
      new SupplierItemGroup(ConstructsArmoryMod.MOD_ID, "armor",
          () -> ArmorItems.helmet.get().getRenderTool());

  private static final Supplier<Item.Properties> ARMOR =
      () -> new Item.Properties().group(TAB_ARMOR);

  public static final ItemObject<ModifiableArmorItem> helmet = ITEMS.register("helmet",
      () -> new ModifiableArmorItem(EquipmentSlotType.HEAD, ARMOR.get(), ArmorDefinitions.HELMET));
  public static final ItemObject<ModifiableArmorItem> chestplate = ITEMS.register("chestplate",
      () -> new ModifiableArmorItem(EquipmentSlotType.CHEST, ARMOR.get(),
          ArmorDefinitions.CHESTPLATE));
  public static final ItemObject<ModifiableArmorItem> leggings = ITEMS.register("leggings",
      () -> new ModifiableArmorItem(EquipmentSlotType.LEGS, ARMOR.get(),
          ArmorDefinitions.LEGGINGS));
  public static final ItemObject<ModifiableArmorItem> boots = ITEMS.register("boots",
      () -> new ModifiableArmorItem(EquipmentSlotType.FEET, ARMOR.get(), ArmorDefinitions.BOOTS));
}
