package top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter;

import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

    if (attacker instanceof LivingEntity && attacker.isAlive() && isDirectDamage) {
      int durabilityDamage =
          counter(tool, level, (LivingEntity) attacker, context, slotType, source, amount);

      if (durabilityDamage > 0) {
        ToolDamageUtil.damageAnimated(tool, durabilityDamage, context.getEntity(), slotType);
      }
    }
  }

  protected abstract int counter(@Nonnull IModifierToolStack tool, int level,
                                 LivingEntity attacker, @Nonnull EquipmentContext context,
                                 @Nonnull EquipmentSlotType slotType, DamageSource source,
                                 float amount);
}
