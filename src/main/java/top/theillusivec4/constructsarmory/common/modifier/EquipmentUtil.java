package top.theillusivec4.constructsarmory.common.modifier;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.Util;

public class EquipmentUtil {

  private static final Map<ModifierId, Map<EquipmentSlotType, UUID>> UUIDS = new HashMap<>();

  public static UUID getUuid(ModifierId id, EquipmentSlotType slotType) {
    return UUIDS.computeIfAbsent(id, (k) -> new EnumMap<>(EquipmentSlotType.class))
        .computeIfAbsent(slotType, (k) -> {
          String key = id + slotType.toString();
          return UUID.nameUUIDFromBytes(key.getBytes());
        });
  }

  public static void addResistanceTooltip(Modifier modifier, IModifierToolStack armor,
                                          float multiplier, List<ITextComponent> tooltip) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      tooltip.add(modifier.applyStyle(new StringTextComponent(
          Util.PERCENT_BOOST_FORMAT.format(multiplier / 25f))
          .appendString(" ")
          .appendSibling(
              new TranslationTextComponent(modifier.getTranslationKey() + ".resistance"))));
    }
  }

  public static void addSpeedTooltip(Modifier modifier, IModifierToolStack armor, float multiplier,
                                     List<ITextComponent> tooltip) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      tooltip.add(modifier.applyStyle(new StringTextComponent(
          Util.PERCENT_BOOST_FORMAT.format(multiplier))
          .appendString(" ")
          .appendSibling(
              new TranslationTextComponent(modifier.getTranslationKey() + ".speed"))));
    }
  }
}
