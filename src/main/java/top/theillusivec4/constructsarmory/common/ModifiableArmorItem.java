package top.theillusivec4.constructsarmory.common;

import javax.annotation.Nullable;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ToolItem;

public class ModifiableArmorItem extends ToolItem {

  protected final EquipmentSlotType slotType;

  protected ModifiableArmorItem(EquipmentSlotType slotType, Properties properties,
                                ToolDefinition toolDefinition) {
    super(properties, toolDefinition);
    this.slotType = slotType;
  }

  @Nullable
  @Override
  public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
    return this.slotType;
  }
}
