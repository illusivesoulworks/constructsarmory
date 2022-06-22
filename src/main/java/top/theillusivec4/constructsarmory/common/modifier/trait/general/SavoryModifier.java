package top.theillusivec4.constructsarmory.common.modifier.trait.general;

import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.shared.TinkerCommons;

public class SavoryModifier extends Modifier {

  public SavoryModifier() {
    super(0xf0a8a4);
  }

  @Override
  public void onAttacked(@Nonnull IModifierToolStack tool, int level,
                         @Nonnull EquipmentContext context, @Nonnull EquipmentSlotType slotType,
                         @Nonnull DamageSource source, float amount, boolean isDirectDamage) {

    if (amount > 0) {
      LivingEntity livingEntity = context.getEntity();

      if (livingEntity.hurtResistantTime <= 10 &&
          RANDOM.nextInt(24 / level) <= (Math.log(amount + 1.0f) * 2.0f)) {
        context.getEntity().entityDropItem(new ItemStack(TinkerCommons.bacon));
      }
    }
  }
}
