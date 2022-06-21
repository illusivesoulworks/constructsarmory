package top.theillusivec4.constructsarmory.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.mantle.util.SupplierItemGroup;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.common.item.MaterialArmorItem;

public class ConstructsArmoryItems {

  private static final ItemDeferredRegisterExtension
      ITEMS = new ItemDeferredRegisterExtension(ConstructsArmoryMod.MOD_ID);

  // Armor
  public static final ItemGroup TAB_ARMOR =
      new SupplierItemGroup(ConstructsArmoryMod.MOD_ID, "armor",
          () -> ConstructsArmoryItems.MATERIAL_ARMOR.get(ArmorSlotType.HELMET).getRenderTool());

  private static final Supplier<Item.Properties> ARMOR =
      () -> new Item.Properties().group(TAB_ARMOR);

  public static final EnumObject<ArmorSlotType, ModifiableArmorItem> MATERIAL_ARMOR =
      ITEMS.registerEnum("material_armor", ArmorSlotType.values(),
          type -> new MaterialArmorItem(ConstructsArmoryDefinitions.MATERIAL_ARMOR, type,
              ARMOR.get()));

  // Armor Parts
  public static final ItemGroup TAB_ARMOR_PARTS =
      new SupplierItemGroup(ConstructsArmoryMod.MOD_ID, "armor_parts", () -> {
        List<IMaterial> materials =
            new ArrayList<>(MaterialRegistry.getInstance().getVisibleMaterials());

        if (materials.isEmpty()) {
          return new ItemStack(ConstructsArmoryItems.HEAD_PLATE);
        }
        return ConstructsArmoryItems.HEAD_PLATE.get()
            .withMaterial(materials.get(TConstruct.RANDOM.nextInt(materials.size())));
      });

  private static final Item.Properties PARTS_PROPS = new Item.Properties().group(TAB_ARMOR_PARTS);

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
      new Item.Properties().group(TinkerSmeltery.TAB_SMELTERY);

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
