package c4.conarm.armor.traits;

import c4.conarm.ConstructsArmory;
import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TraitAmbitious extends AbstractArmorTrait {

    private static final double XP_MOD_PER_LEVEL = 0.025D;

    public TraitAmbitious() {
        super("ambitious", 0xffffff);

        MinecraftForge.EVENT_BUS.register(this);
    }

//    @SubscribeEvent
//    public void onXPDrop(LivingExperienceDropEvent evt) {
//        int xpValue = evt.getDroppedExperience();
//        if(xpValue > 0) {
//            int level = ArmorHelper.getArmorAbilityLevel(evt.getAttackingPlayer(), this.identifier);
//            evt.setDroppedExperience((int) (xpValue * (1 + XP_MOD_PER_LEVEL * level)));
//        }
//    }

    @SubscribeEvent
    public void onXPPickUp(PlayerPickupXpEvent evt) {
        int xpValue = evt.getOrb().getXpValue();
        if (xpValue > 0) {
            int level = ArmorHelper.getArmorAbilityLevel(evt.getEntityPlayer(), this.identifier);
            int addXP = random.nextInt(level + 1);
            if (addXP > 0) {
                evt.getEntityPlayer().addExperience(addXP);
            }
        }
    }
}
