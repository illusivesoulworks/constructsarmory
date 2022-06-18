package top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter;

import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class EnkindlingModifier extends CounterattackModifier {

  public EnkindlingModifier() {
    super(0xdbcc0b);
  }

  @Override
  protected int counter(@Nonnull IModifierToolStack tool, int level, LivingEntity attacker,
                        @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                        DamageSource source, float amount) {

    if (RANDOM.nextFloat() < 0.15f * level) {
      attacker.attackEntityFrom(DamageSource.causeThornsDamage(context.getEntity()).setFireDamage(),
          level);
      attacker.setFire(5 * level);
      return 1;
    }
    return 0;
  }
}
