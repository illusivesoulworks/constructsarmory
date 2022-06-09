package top.theillusivec4.constructsarmory.common.modifier;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.inventory.EquipmentSlotType;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class EquipmentModifierUuids {

  private static final Map<ModifierId, Map<EquipmentSlotType, UUID>> UUIDS = new HashMap<>();

  public static UUID get(ModifierId id, EquipmentSlotType slotType) {
    return UUIDS.computeIfAbsent(id, (k) -> new EnumMap<>(EquipmentSlotType.class))
        .computeIfAbsent(slotType, (k) -> {
          String key = id + slotType.toString();
          return UUID.nameUUIDFromBytes(key.getBytes());
        });
  }
}
