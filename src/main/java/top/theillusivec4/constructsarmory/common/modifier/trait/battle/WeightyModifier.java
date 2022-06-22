package top.theillusivec4.constructsarmory.common.modifier.trait.battle;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class WeightyModifier extends Modifier {

  private static final float BASELINE_MOVEMENT = 0.1f;
  private static final float MAX_MOVEMENT = 0.15f;

  public WeightyModifier() {
    super(0xd1c08b);
  }

  private static float getBonus(float movementSpeed, float min, float max) {

    if (movementSpeed > BASELINE_MOVEMENT) {

      if (movementSpeed > MAX_MOVEMENT) {
        return min;
      }
      return (MAX_MOVEMENT - movementSpeed) / (MAX_MOVEMENT - BASELINE_MOVEMENT) * (max - min);
    }
    return max;
  }

  @Override
  public float getProtectionModifier(@Nonnull IModifierToolStack tool, int level,
                                     @Nonnull EquipmentContext context,
                                     @Nonnull EquipmentSlotType slotType, DamageSource source,
                                     float modifierValue) {

    if (!source.isDamageAbsolute() && !source.canHarmInCreative()) {
      ModifiableAttributeInstance attributeInstance =
          context.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);

      if (attributeInstance != null) {
        modifierValue += getBonus((float) attributeInstance.getValue(), 0.0f, 2.0f) * level;
      }
    }
    return modifierValue;
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack tool, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag flag) {
    float bonus;

    if (player != null) {
      ModifiableAttributeInstance attributeInstance =
          player.getAttribute(Attributes.MOVEMENT_SPEED);

      if (attributeInstance != null) {
        bonus = getBonus((float) attributeInstance.getValue(), 0.0f, 2.0f);
      } else {
        bonus = 0f;
      }
    } else {
      bonus = level * 2f;
    }
    EquipmentUtil.addResistanceTooltip(this, tool, bonus, tooltip);
  }
}
