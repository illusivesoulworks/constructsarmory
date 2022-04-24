package top.theillusivec4.constructsarmory.data;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.IItemProvider;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;

public class ArmorSlotLayoutProvider extends AbstractStationSlotLayoutProvider {

  private static final int SORT_ARMOR = 16;

  public ArmorSlotLayoutProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected void addLayouts() {
    Map<ArmorSlotType, IItemProvider> plates = new HashMap<>();
    plates.put(ArmorSlotType.HELMET, ConstructsArmoryItems.HEAD_PLATE);
    plates.put(ArmorSlotType.CHESTPLATE, ConstructsArmoryItems.BODY_PLATE);
    plates.put(ArmorSlotType.LEGGINGS, ConstructsArmoryItems.LEGS_PLATE);
    plates.put(ArmorSlotType.BOOTS, ConstructsArmoryItems.FEET_PLATE);
    ConstructsArmoryItems.MATERIAL_ARMOR.forEach((slotType, item) ->
        defineModifiable(item)
            .sortIndex(SORT_ARMOR)
            .addInputItem(plates.get(slotType), 30, 48)
            .addInputItem(ConstructsArmoryItems.MAIL, 12, 30)
            .build());
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Tinker Station Slot Layouts";
  }
}
