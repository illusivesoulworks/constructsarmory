package top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter;

import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class PricklyModifier extends CounterattackModifier {

  public PricklyModifier() {
    super(0x601cc4);
  }

  @Override
  protected int counter(@Nonnull IModifierToolStack tool, int level, LivingEntity attacker,
                        @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                        DamageSource source, float amount) {

    if (RANDOM.nextFloat() < 0.15f * level) {
      attacker.setLastAttackedEntity(context.getEntity());
      TinkerModifiers.bleeding.get()
          .apply(attacker, 1 + 20 * (2 + (RANDOM.nextInt(level + 3))), level - 1, true);
      return 1;
    }
    return 0;
  }
}
