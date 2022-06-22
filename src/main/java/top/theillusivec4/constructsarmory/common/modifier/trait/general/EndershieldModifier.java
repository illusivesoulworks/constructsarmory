package top.theillusivec4.constructsarmory.common.modifier.trait.general;

import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.events.teleport.EnderdodgingTeleportEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TeleportHelper;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class EndershieldModifier extends Modifier {

  private static final TeleportHelper.ITeleportEventFactory FACTORY =
      EnderdodgingTeleportEvent::new;

  public EndershieldModifier() {
    super(0xa92dff);
  }

  @Override
  public void onAttacked(@Nonnull IModifierToolStack tool, int level, EquipmentContext context,
                         @Nonnull EquipmentSlotType slotType, @Nonnull DamageSource source,
                         float amount, boolean isDirectDamage) {
    LivingEntity self = context.getEntity();

    if (!self.isPotionActive(TinkerModifiers.teleportCooldownEffect.get()) &&
        RANDOM.nextInt(10 - level * 2) == 0) {

      if (TeleportHelper.randomNearbyTeleport(context.getEntity(), FACTORY)) {
        TinkerModifiers.teleportCooldownEffect.get().apply(self, 15 * 20, 1, true);
      }
    }
  }
}
