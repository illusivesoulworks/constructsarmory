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

public class WovenModifier extends Modifier {

  private final static float MULTIPLIER = 0.005f;
  private final Lazy<String> speedName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".speed";
  });
  private final Lazy<String> armorName = Lazy.of(() -> {
    ResourceLocation id = getId();
    return id.getPath() + "." + id.getNamespace() + ".armor";
  });

  public WovenModifier() {
    super(0xc65c35);
  }

  private float getMultiplier(IModifierToolStack armor, int level) {
    return (float) (Math.sqrt(armor.getDamage() * level / armor.getModifier(ToolStats.DURABILITY)) *
        MULTIPLIER);
  }

  @Override
  public void addAttributes(@Nonnull IModifierToolStack armor, int level,
                            @Nonnull EquipmentSlotType slot,
                            @Nonnull BiConsumer<Attribute, AttributeModifier> consumer) {

    if (slot.getSlotType() == EquipmentSlotType.Group.ARMOR) {
      float boost = getMultiplier(armor, level);

      if (boost != 0) {
        UUID uuid = EquipmentUtil.getUuid(getId(), slot);
        consumer.accept(Attributes.ARMOR, new AttributeModifier(uuid, armorName.get(), -boost * 2,
            AttributeModifier.Operation.MULTIPLY_TOTAL));
        consumer.accept(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(uuid, speedName.get(), boost / 2,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
      }
    }
  }
}
