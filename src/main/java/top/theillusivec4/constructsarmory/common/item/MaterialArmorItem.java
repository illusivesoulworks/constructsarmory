package top.theillusivec4.constructsarmory.common.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.client.DynamicTextureGenerator;

public class MaterialArmorItem extends ModifiableArmorItem {

  public MaterialArmorItem(ModifiableArmorMaterial material, ArmorSlotType slotType,
                           Properties properties) {
    super(material, slotType, properties);
  }

  @Nullable
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot,
                                String type) {
    return DynamicTextureGenerator.getCachedTexture(stack, slot == EquipmentSlotType.LEGS);
  }
}
