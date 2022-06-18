package top.theillusivec4.constructsarmory.common.modifier.trait.speed;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ArmorSpeedTradeModifier extends Modifier {

  private final float multiplier;
  private final Map<EquipmentSlotType, UUID> uuid = new EnumMap<>(EquipmentSlotType.class);
  private final Lazy<String> attributeName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".attack_damage";
  });

  /**
   * @param color      Modifier text color
   * @param multiplier Multiplier. Positive boosts armor, negative boosts movement speed
   */
  public ArmorSpeedTradeModifier(int color, float multiplier) {
    super(color);
    this.multiplier = multiplier;
  }

  /**
   * Gets the multiplier for this modifier at the current durability and level
   */
  private double getMultiplier(IModifierToolStack armor, int level) {
    return Math.sqrt(armor.getDamage() * level / armor.getModifier(ToolStats.DURABILITY)) *
        multiplier;
  }

  @Override
  public void addAttributes(@Nonnull IModifierToolStack armor, int level,
                            @Nonnull EquipmentSlotType slot,
                            @Nonnull BiConsumer<Attribute, AttributeModifier> consumer) {

    if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR) {
      double boost = getMultiplier(armor, level);

      if (boost != 0) {
        consumer.accept(Attributes.ARMOR,
            new AttributeModifier(getUuid(slot), attributeName.get(), boost * 0.8d,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
        consumer.accept(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(getUuid(slot), attributeName.get(), -boost,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
      }
    }
  }

  private UUID getUuid(EquipmentSlotType slotType) {
    return uuid.computeIfAbsent(slotType, (k) -> {
      String key = getId() + slotType.toString();
      return UUID.nameUUIDFromBytes(key.getBytes());
    });
  }
}
