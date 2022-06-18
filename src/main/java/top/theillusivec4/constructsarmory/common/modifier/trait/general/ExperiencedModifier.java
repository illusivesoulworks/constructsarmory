package top.theillusivec4.constructsarmory.common.modifier.trait.general;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class ExperiencedModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> EXPERIENCED =
      ConstructsArmoryMod.createKey("experienced");

  public ExperiencedModifier() {
    super(0xe8db49, EXPERIENCED);
    MinecraftForge.EVENT_BUS.addListener(this::onEntityKill);
    MinecraftForge.EVENT_BUS.addListener(this::beforeBlockBreak);
  }

  private static int boost(int original, int level) {
    return (int) (original * (1 + (0.25 * level)));
  }

  private void beforeBlockBreak(final BlockEvent.BreakEvent evt) {
    evt.getPlayer().getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
      int levels = data.get(EXPERIENCED, 0);

      if (levels > 0) {
        evt.setExpToDrop(boost(evt.getExpToDrop(), levels));
      }
    });
  }

  private void onEntityKill(final LivingExperienceDropEvent evt) {
    PlayerEntity player = evt.getAttackingPlayer();

    if (player != null) {
      player.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
        int levels = data.get(EXPERIENCED, 0);

        if (levels > 0) {
          evt.setDroppedExperience(boost(evt.getDroppedExperience(), levels));
        }
      });
    }
  }
}
