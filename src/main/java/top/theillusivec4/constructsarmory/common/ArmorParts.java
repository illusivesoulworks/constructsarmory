package top.theillusivec4.constructsarmory.common;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.mantle.util.SupplierItemGroup;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;

public final class ArmorParts extends ConstructsArmoryModule {

  public static final ItemGroup TAB_ARMOR_PARTS =
      new SupplierItemGroup(ConstructsArmoryMod.MOD_ID, "armor_parts", () -> {
        List<IMaterial> materials =
            new ArrayList<>(MaterialRegistry.getInstance().getVisibleMaterials());

        if (materials.isEmpty()) {
          return new ItemStack(ArmorParts.chestplateCore);
        }
        return ArmorParts.chestplateCore.get()
            .withMaterial(materials.get(TConstruct.RANDOM.nextInt(materials.size())));
      });

  private static final Item.Properties PARTS_PROPS = new Item.Properties().group(TAB_ARMOR_PARTS);

  public static final ItemObject<ToolPartItem> helmetCore =
      ITEMS.register("helmet_core", () -> new ToolPartItem(PARTS_PROPS,
          ArmorMaterialStatsIdentifiers.HELMET_CORE));
  public static final ItemObject<ToolPartItem> chestplateCore = ITEMS.register("chestplate_core",
      () -> new ToolPartItem(PARTS_PROPS, ArmorMaterialStatsIdentifiers.CHESTPLATE_CORE));
  public static final ItemObject<ToolPartItem> leggingsCore = ITEMS.register("leggings_core",
      () -> new ToolPartItem(PARTS_PROPS, ArmorMaterialStatsIdentifiers.LEGGINGS_CORE));
  public static final ItemObject<ToolPartItem> bootsCore = ITEMS.register("boots_core",
      () -> new ToolPartItem(PARTS_PROPS, ArmorMaterialStatsIdentifiers.BOOTS_CORE));
  public static final ItemObject<ToolPartItem> armorPlate = ITEMS.register("armor_plate",
      () -> new ToolPartItem(PARTS_PROPS, ArmorMaterialStatsIdentifiers.PLATE));
  public static final ItemObject<ToolPartItem> armorTrim = ITEMS.register("armor_trim",
      () -> new ToolPartItem(PARTS_PROPS, ArmorMaterialStatsIdentifiers.TRIM));
}
