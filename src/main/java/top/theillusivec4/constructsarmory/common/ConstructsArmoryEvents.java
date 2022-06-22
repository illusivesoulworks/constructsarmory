package top.theillusivec4.constructsarmory.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.CombatRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.helper.ArmorUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.modifier.IArmorUpdateModifier;

public class ConstructsArmoryEvents {

  public static void setup() {
    MinecraftForge.EVENT_BUS.addListener(ConstructsArmoryEvents::livingUpdate);
    MinecraftForge.EVENT_BUS.addListener(ConstructsArmoryEvents::livingHurt);
//    MinecraftForge.EVENT_BUS.addListener(ConstructsArmoryEvents::livingDamage);
  }
//
//  private static void livingDamage(final LivingDamageEvent evt) {
//
//    if (evt.getEntityLiving() instanceof PlayerEntity) {
//      ConstructsArmoryMod.LOGGER.info(evt.getAmount());
//    }
//  }

  private static void livingHurt(final LivingHurtEvent evt) {
    LivingEntity living = evt.getEntityLiving();
    EquipmentContext context = new EquipmentContext(living);

    if (!context.hasModifiableArmor()) {
      return;
    }

    if (!evt.getSource().isUnblockable()) {
      ModifiableAttributeInstance armorAtt = living.getAttribute(Attributes.ARMOR);
      float armor = 0;

      if (armorAtt != null) {
        armor = (float) armorAtt.getValue();
      }

      if (armor % 1 == 0) {
        return;
      }
      ModifiableAttributeInstance toughnessAtt = living.getAttribute(Attributes.ARMOR_TOUGHNESS);
      float toughness = 0;

      if (toughnessAtt != null) {
        toughness = (float) toughnessAtt.getValue();
      }
      float damage = CombatRules.getDamageAfterAbsorb(evt.getAmount(), armor, toughness);
      evt.setAmount(
          ArmorUtil.getDamageBeforeArmorAbsorb(damage, living.getTotalArmorValue(), toughness));
    }
  }

  private static void livingUpdate(final LivingEvent.LivingUpdateEvent evt) {
    LivingEntity living = evt.getEntityLiving();

    if (living.isSpectator()) {
      return;
    }
    EquipmentContext context = new EquipmentContext(living);

    if (!context.hasModifiableArmor()) {
      return;
    }

    if (!living.world.isRemote() && living.isAlive() && living.ticksExisted % 20 == 0) {

      for (EquipmentSlotType slotType : ModifiableArmorMaterial.ARMOR_SLOTS) {
        IModifierToolStack armor = context.getToolInSlot(slotType);

        if (armor != null) {

          for (ModifierEntry entry : armor.getModifierList()) {
            IArmorUpdateModifier hook = entry.getModifier().getModule(IArmorUpdateModifier.class);

            if (hook != null) {
              hook.onUpdate(armor, slotType, entry.getLevel(), living);
            }
          }
        }
      }
    }
  }
}
