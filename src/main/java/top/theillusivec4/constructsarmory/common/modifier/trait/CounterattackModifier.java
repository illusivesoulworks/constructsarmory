package top.theillusivec4.constructsarmory.common.modifier.trait;

import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public abstract class CounterattackModifier extends Modifier {

  public CounterattackModifier(int color) {
    super(color);
  }

  @Override
  public void onAttacked(@Nonnull IModifierToolStack tool, int level,
                         @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                         DamageSource source, float amount, boolean isDirectDamage) {
    Entity attacker = source.getTrueSource();

    if (attacker != null && isDirectDamage &&
        counter(tool, level, attacker, context, slotType, source, amount)) {
      ToolDamageUtil.damageAnimated(tool, 1, context.getEntity(), slotType);
    }
  }

  protected abstract boolean counter(@Nonnull IModifierToolStack tool, float level, Entity attacker,
                                     @Nonnull EquipmentContext context,
                                     @Nonnull EquipmentSlotType slotType, DamageSource source,
                                     float amount);
}
