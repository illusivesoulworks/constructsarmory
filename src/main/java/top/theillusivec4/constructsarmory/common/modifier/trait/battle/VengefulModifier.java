package top.theillusivec4.constructsarmory.common.modifier.trait.battle;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryEffects;
import top.theillusivec4.constructsarmory.common.modifier.EquipmentUtil;

public class VengefulModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> VENGEFUL =
      ConstructsArmoryMod.createKey("vengeful");

  public VengefulModifier() {
    super(0x9261cc, VENGEFUL);
    MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, VengefulModifier::onHurt);
  }

  private static void onHurt(final LivingHurtEvent evt) {
    LivingEntity living = evt.getEntityLiving();
    living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(holder -> {
      int levels = holder.get(VENGEFUL, 0);

      if (levels > 0) {
        int effectLevel = Math.min(7, ConstructsArmoryEffects.VENGEFUL.get().getLevel(living) + 1);
        ConstructsArmoryEffects.VENGEFUL.get().apply(living, 5 * 20, effectLevel, true);
      }
    });
  }

  private static float getBonus(LivingEntity attacker, int level) {
    int effectLevel = ConstructsArmoryEffects.VENGEFUL.get().getLevel(attacker) + 1;
    return level * effectLevel / 8f;
  }

  @Override
  public float getProtectionModifier(@Nonnull IModifierToolStack tool, int level,
                                     @Nonnull EquipmentContext context,
                                     @Nonnull EquipmentSlotType slotType, DamageSource source,
                                     float modifierValue) {

    if (!source.isDamageAbsolute() && !source.canHarmInCreative()) {
      modifierValue += getBonus(context.getEntity(), level);
    }
    return modifierValue;
  }

  @Override
  public void addInformation(@Nonnull IModifierToolStack tool, int level,
                             @Nullable PlayerEntity player, @Nonnull List<ITextComponent> tooltip,
                             @Nonnull TooltipKey key, @Nonnull TooltipFlag flag) {

    float bonus;

    if (player != null && key == TooltipKey.SHIFT) {
      bonus = getBonus(player, level);
    } else {
      bonus = 2f;
    }
    EquipmentUtil.addResistanceTooltip(this, tool, level, bonus, tooltip);
  }
}
