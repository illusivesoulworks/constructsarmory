package top.theillusivec4.constructsarmory.common.modifier.trait.speed;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class SalvagedModifier extends AbstractSpeedModifier {

  public SalvagedModifier() {
    super(0x7e6059);
  }

  public static float boost(int durability, float boost, int min, int max) {

    if (durability > min) {

      if (durability > max) {
        return 0.0f;
      }
      return boost * (1 - (float) durability / max);
    }
    return boost;
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack armor, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      float boost;

      if (player != null && key == TooltipKey.SHIFT) {
        boost = getTotalBoost(armor, level);
      } else {
        boost = 0.025f * level;
      }

      if (boost > 0) {
        EquipmentUtil.addSpeedTooltip(this, armor, boost, tooltip);
      }
    }
  }

  protected float getTotalBoost(IModifierToolStack armor, int level) {
    int durability = armor.getCurrentDurability();
    int fullMax = armor.getStats().getInt(ToolStats.DURABILITY);
    return boost(durability, 0.025f, 1, (int) (fullMax * 0.75f)) * level;
  }

  @Override
  protected void applyBoost(IModifierToolStack armor, EquipmentSlotType slotType,
                            ModifiableAttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    float boost = getTotalBoost(armor, level);

    if (boost > 0) {
      attribute.applyNonPersistentModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.salvaged", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
