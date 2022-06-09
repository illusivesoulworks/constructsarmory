package top.theillusivec4.constructsarmory.common.modifier.trait;

import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class DelvingModifier extends TotalArmorLevelModifier {

  private static final TinkerDataCapability.TinkerDataKey<Integer> DELVING =
      ConstructsArmoryMod.createKey("delving");
  private static final UUID ID = UUID.fromString("0b787cea-7133-441b-93eb-76c35b74b9cc");
  private static final int SEA_LEVEL = 64;
  private static final float BOOST_AT_0 = 0.1f;

  private static float getBoost(int y, int level) {
    return (SEA_LEVEL - y) * level * (BOOST_AT_0 / SEA_LEVEL);
  }

  public DelvingModifier() {
    super(0xf98648, DELVING);
    MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false,
        LivingEvent.LivingUpdateEvent.class, DelvingModifier::tick);
  }

  private static void tick(final LivingEvent.LivingUpdateEvent evt) {
    LivingEntity livingEntity = evt.getEntityLiving();

    if (livingEntity.ticksExisted % 20 == 0) {
      ModifiableAttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

      if (attribute != null) {
        attribute.removeModifier(ID);
        livingEntity.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(data -> {
          int levels = data.get(DELVING, 0);

          if (levels > 0) {
            float boost = getBoost((int) livingEntity.getPosY(), levels);
            attribute.applyNonPersistentModifier(
                new AttributeModifier(ID, "constructsarmory.modifier.delving", boost,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));
          }
        });
      }
    }
  }
}
