package top.theillusivec4.constructsarmory.common.modifier.trait.speed;

import java.util.UUID;
import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.ForgeMod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class AerialModifier extends Modifier {

  public AerialModifier() {
    super(0x00f4da);
  }

  @Override
  public void addAttributes(@Nonnull IModifierToolStack tool, int level,
                            @Nonnull EquipmentSlotType slot,
                            @Nonnull BiConsumer<Attribute, AttributeModifier> consumer) {

    if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR) {
      UUID uuid = EquipmentUtil.getUuid(getId(), slot);
      consumer.accept(ForgeMod.ENTITY_GRAVITY.get(),
          new AttributeModifier(uuid, "constructsarmory.modifier.aerial." + slot.getName(),
              -(0.2f * level), AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
