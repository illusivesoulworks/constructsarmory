package top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter;

import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class SplinteredModifier extends CounterattackModifier {

  public SplinteredModifier() {
    super(0xe8e5d2);
  }

  @Override
  protected int counter(@Nonnull IModifierToolStack tool, int level, LivingEntity attacker,
                        @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                        DamageSource source, float amount) {

    if (RANDOM.nextFloat() < 0.15f * level) {
      float percent = (float) tool.getDamage() / tool.getStats().getFloat(ToolStats.DURABILITY);
      float maxDamage = level * 3f;
      LivingEntity user = context.getEntity();
      attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), maxDamage * percent);
      return 1;
    }
    return 0;
  }
}
