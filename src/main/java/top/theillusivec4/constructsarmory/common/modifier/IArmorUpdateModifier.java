package top.theillusivec4.constructsarmory.common.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public interface IArmorUpdateModifier {

  void onUpdate(IModifierToolStack armor, EquipmentSlotType slotType, int level,
                LivingEntity living);
}
