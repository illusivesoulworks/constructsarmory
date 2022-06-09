package top.theillusivec4.constructsarmory.common.modifier.trait;

import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
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
  protected boolean counter(@Nonnull IModifierToolStack tool, float level, Entity attacker,
                            @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                            DamageSource source, float amount) {
    float percent = (float) tool.getDamage() / tool.getStats().getFloat(ToolStats.DURABILITY);
    float maxDamage = level * 0.225f;
    LivingEntity user = context.getEntity();
    attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), maxDamage * percent);
    return false;
  }
}
