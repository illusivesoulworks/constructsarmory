package top.theillusivec4.constructsarmory.common.modifier.trait.general;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class ShieldingModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> SHIELDING =
      ConstructsArmoryMod.createKey("shielding");

  public ShieldingModifier() {
    super(0x575e79, SHIELDING);
    MinecraftForge.EVENT_BUS.addListener(ShieldingModifier::onPotionStart);
  }

  private static void onPotionStart(final PotionEvent.PotionAddedEvent evt) {
    EffectInstance newEffect = evt.getPotionEffect();

    if (!newEffect.getCurativeItems().isEmpty()) {
      LivingEntity living = evt.getEntityLiving();
      living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
        int levels = data.get(SHIELDING, 0);

        if (levels > 0) {
          float change = levels * 0.05f;

          if (!newEffect.getPotion().isBeneficial()) {
            change *= -1;
          }
          newEffect.duration = Math.max(0, (int) (newEffect.getDuration() * (1 + change)));
        }
      });
    }
  }
}
