package top.theillusivec4.constructsarmory.common.item;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.client.MaterialArmorModel;

public class MaterialArmorItem extends ModifiableArmorItem {

  public MaterialArmorItem(ModifiableArmorMaterial material, ArmorSlotType slotType,
                           Properties properties) {
    super(material, slotType, properties);
  }

  @Nullable
  @Override
  @OnlyIn(Dist.CLIENT)
  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack,
                                                   EquipmentSlotType armorSlot, A base) {
    return MaterialArmorModel.getModel(stack, armorSlot, base);
  }
}
