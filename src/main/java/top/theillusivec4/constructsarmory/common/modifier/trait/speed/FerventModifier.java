package top.theillusivec4.constructsarmory.common.modifier.trait.speed;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class FerventModifier extends AbstractSpeedModifier {

  private static final float BASELINE_TEMPERATURE = 0.75f;

  public FerventModifier() {
    super(0x9c5643);
  }

  private static float getBonus(LivingEntity player, BlockPos pos, int level) {
    return Math.abs(player.world.getBiome(pos).getTemperature(pos) - BASELINE_TEMPERATURE) * level /
        62.5f;
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack armor, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {
    float bonus;

    if (player != null && key == TooltipKey.SHIFT) {
      bonus = getBonus(player, player.getPosition(), level);
    } else {
      bonus = level * 0.125f;
    }
    if (bonus >= 0.01f) {
      EquipmentUtil.addSpeedTooltip(this, armor, bonus, tooltip);
    }
  }

  @Override
  protected void applyBoost(IModifierToolStack armor, EquipmentSlotType slotType,
                            ModifiableAttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    float boost = getBonus(living, living.getPosition(), level);

    if (boost > 0) {
      attribute.applyNonPersistentModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.fervent", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
