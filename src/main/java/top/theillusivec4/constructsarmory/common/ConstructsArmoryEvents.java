package top.theillusivec4.constructsarmory.common;

import java.util.Objects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import top.theillusivec4.constructsarmory.common.modifier.IArmorUpdateModifier;

public class ConstructsArmoryEvents {

  public static void setup() {
    MinecraftForge.EVENT_BUS.addListener(ConstructsArmoryEvents::livingUpdate);
  }

  private static void livingUpdate(final LivingEvent.LivingUpdateEvent event) {
    LivingEntity living = event.getEntityLiving();

    if (living.isSpectator()) {
      return;
    }
    EquipmentContext context = new EquipmentContext(living);

    if (!context.hasModifiableArmor()) {
      return;
    }

    if (!living.world.isRemote() && living.isAlive() && living.ticksExisted % 20 == 0) {

      for (EquipmentSlotType slotType : ModifiableArmorMaterial.ARMOR_SLOTS) {
        IModifierToolStack armor = context.getToolInSlot(slotType);

        if (armor != null) {

          for (ModifierEntry entry : armor.getModifierList()) {
            IArmorUpdateModifier hook = entry.getModifier().getModule(IArmorUpdateModifier.class);

            if (hook != null) {
              hook.onUpdate(armor, slotType, entry.getLevel(), living);
            }
          }
        }
      }
    }
  }
}
