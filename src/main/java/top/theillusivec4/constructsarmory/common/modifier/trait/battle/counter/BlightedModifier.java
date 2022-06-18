package top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter;

import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class BlightedModifier extends CounterattackModifier {

  public BlightedModifier() {
    super(0x7f9374);
  }

  private static EffectInstance makeDecayEffect(int level) {
    return new EffectInstance(Effects.WITHER, 20 * (5 + (RANDOM.nextInt(level * 3))), level - 1);
  }

  @Override
  protected int counter(@Nonnull IModifierToolStack tool, int level, LivingEntity attacker,
                        @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                        DamageSource source, float amount) {

    if (RANDOM.nextFloat() < 0.15f * level) {

      if (RANDOM.nextInt(3) == 0) {
        context.getEntity().addPotionEffect(makeDecayEffect(level));
      }
      attacker.addPotionEffect(makeDecayEffect(level));
      return 1;
    }
    return 0;
  }
}
