package top.theillusivec4.constructsarmory.common.modifier.trait.battle;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class HallowedModifier extends Modifier {

  public HallowedModifier() {
    super(0xcc9720);
  }

  @Override
  public float getProtectionModifier(@Nonnull IModifierToolStack tool, int level,
                                     @Nonnull EquipmentContext context,
                                     @Nonnull EquipmentSlotType slotType, DamageSource source,
                                     float modifierValue) {

    if (!source.isDamageAbsolute() && !source.canHarmInCreative()) {
      Entity attacker = source.getTrueSource();

      if (attacker instanceof LivingEntity && ((LivingEntity) attacker).isEntityUndead()) {
        modifierValue += 2f;
      }
    }
    return modifierValue;
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack tool, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag flag) {
    EquipmentUtil.addResistanceTooltip(this, tool, level, 2f, tooltip);
  }
}
