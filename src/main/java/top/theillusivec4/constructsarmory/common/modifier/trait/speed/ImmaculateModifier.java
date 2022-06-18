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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.context.ToolRebuildContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class ImmaculateModifier extends AbstractSpeedModifier {

  private static final ResourceLocation KEY_ORIGINAL_DURABILITY =
      TConstruct.getResource("durability");

  public ImmaculateModifier() {
    super(0xe8b465);
  }

  protected ImmaculateModifier(int color) {
    super(color);
  }

  @Override
  public void addVolatileData(@Nonnull ToolRebuildContext context, int level,
                              ModDataNBT volatileData) {
    volatileData.putInt(KEY_ORIGINAL_DURABILITY,
        (int) (context.getStats().getFloat(ToolStats.DURABILITY) *
            context.getDefinition().getData().getMultiplier(ToolStats.DURABILITY)));
  }

  public static float boost(int durability, float boost, int min, int max) {

    if (durability > min) {

      if (durability > max) {
        return boost;
      }
      return boost * (durability - min) / (max - min);
    }
    return 0.0f;
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
        boost = 0.02f * level;
      }

      if (boost > 0) {
        EquipmentUtil.addSpeedTooltip(this, armor, boost, tooltip);
      }
    }
  }

  protected float getTotalBoost(IModifierToolStack armor, int level) {
    int durability = armor.getCurrentDurability();
    int baseMax = armor.getVolatileData().getInt(KEY_ORIGINAL_DURABILITY);
    float boost = boost(durability, 0.02f, baseMax / 2, baseMax);
    int fullMax = armor.getStats().getInt(ToolStats.DURABILITY);

    if (fullMax > baseMax) {
      boost += boost(durability, 0.01f, baseMax, Math.max(baseMax * 2, fullMax));
    }
    return boost * level;
  }

  @Override
  protected void applyBoost(IModifierToolStack armor, EquipmentSlotType slotType,
                            ModifiableAttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    float boost = getTotalBoost(armor, level);

    if (boost > 0) {
      attribute.applyNonPersistentModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.immaculate", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
