package top.theillusivec4.constructsarmory.common.modifier.trait.battle;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolRebuildContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import slimeknights.tconstruct.tools.TinkerModifiers;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class IgneousModifier extends Modifier {

  private static final float BASELINE_TEMPERATURE = 0.75f;

  public IgneousModifier() {
    super(0x4f4a47);
  }

  @Override
  public void addVolatileData(@Nonnull ToolRebuildContext context, int level,
                              @Nonnull ModDataNBT volatileData) {
    TinkerModifiers.tank.get().addCapacity(volatileData, FluidValues.INGOT * 2);
  }

  private static float temperatureBoost(LivingEntity living, int level) {
    BlockPos attackerPos = living.getPosition();
    return (living.world.getBiome(attackerPos).getTemperature(attackerPos) - BASELINE_TEMPERATURE) *
        (level * 2.4f);
  }

  @Override
  public float getProtectionModifier(@Nonnull IModifierToolStack tool, int level,
                                     @Nonnull EquipmentContext context,
                                     @Nonnull EquipmentSlotType slotType, DamageSource source,
                                     float modifierValue) {

    if (!source.isDamageAbsolute() && !source.canHarmInCreative()) {
      modifierValue += temperatureBoost(context.getEntity(), level);
    }
    return modifierValue;
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack tool, int level,
                             @Nullable PlayerEntity player,
                             @Nonnull List<ITextComponent> tooltip, @Nonnull TooltipKey key,
                             @Nonnull TooltipFlag flag) {
    float bonus;

    if (player != null && key == TooltipKey.SHIFT) {
      bonus = temperatureBoost(player, level);
    } else {
      bonus = 3f;
    }
    EquipmentUtil.addResistanceTooltip(this, tool, level, bonus, tooltip);
  }
}
