package top.theillusivec4.constructsarmory.common.modifier.trait.speed;

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
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryStats;

public class ArmorSpeedTradeModifier extends Modifier {

  private final float multiplier;
  private final Lazy<String> speedName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".speed";
  });
  private final Lazy<String> armorName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".armor";
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
  private float getMultiplier(IModifierToolStack armor, int level) {
    return (float) (Math.sqrt(armor.getDamage() * level / armor.getModifier(ToolStats.DURABILITY)) *
        multiplier);
  }

  @Override
  public void addAttributes(@Nonnull IModifierToolStack armor, int level,
                            @Nonnull EquipmentSlotType slot,
                            @Nonnull BiConsumer<Attribute, AttributeModifier> consumer) {

    if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR) {
      float boost = getMultiplier(armor, level);
      float movementBonus = armor.getStats().getFloat(ConstructsArmoryStats.MOVEMENT_SPEED);
      float floor = (1f - 1f / (1f + movementBonus)) * -1f;

      if (boost != 0) {
        UUID uuid = EquipmentUtil.getUuid(getId(), slot);
        consumer.accept(Attributes.ARMOR, new AttributeModifier(uuid, armorName.get(), boost * 0.8f,
            AttributeModifier.Operation.MULTIPLY_TOTAL));
        consumer.accept(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(uuid, speedName.get(), Math.max(-boost, floor),
                AttributeModifier.Operation.MULTIPLY_TOTAL));
      }
    }
  }
}
