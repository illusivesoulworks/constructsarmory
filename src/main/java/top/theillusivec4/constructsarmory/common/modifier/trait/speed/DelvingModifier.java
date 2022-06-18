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
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class DelvingModifier extends AbstractSpeedModifier {

  private static final int SEA_LEVEL = 64;
  private static final float BOOST_AT_0 = 0.02f;

  private static float getBoost(int y, int level) {
    return (SEA_LEVEL - y) * level * (BOOST_AT_0 / SEA_LEVEL);
  }

  public DelvingModifier() {
    super(0xf98648);
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack armor, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag tooltipFlag) {

    if (armor.hasTag(TinkerTags.Items.ARMOR)) {
      float boost;

      if (player != null && key == TooltipKey.SHIFT) {
        boost = getBoost((int) player.getPosY(), level);
      } else {
        boost = BOOST_AT_0 * level;
      }

      if (boost > 0) {
        EquipmentUtil.addSpeedTooltip(this, armor, boost, tooltip);
      }
    }
  }

  @Override
  protected void applyBoost(IModifierToolStack armor, EquipmentSlotType slotType,
                            ModifiableAttributeInstance attribute, UUID uuid, int level,
                            LivingEntity living) {
    float boost = getBoost((int) living.getPosY(), level);

    if (boost > 0) {
      attribute.applyNonPersistentModifier(
          new AttributeModifier(uuid, "constructsarmory.modifier.delving", boost,
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
  }
}
