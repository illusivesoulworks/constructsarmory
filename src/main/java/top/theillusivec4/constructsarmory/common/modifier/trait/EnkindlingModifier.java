package top.theillusivec4.constructsarmory.common.modifier.trait;

import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class EnkindlingModifier extends CounterattackModifier {

  public EnkindlingModifier() {
    super(0xdbcc0b);
  }

  @Override
  protected boolean counter(@Nonnull IModifierToolStack tool, float level, Entity attacker,
                            @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                            DamageSource source, float amount) {
    attacker.attackEntityFrom(DamageSource.causeThornsDamage(context.getEntity()).setFireDamage(),
        level);
    attacker.setFire((int) (5 * level));
    return false;
  }
}
