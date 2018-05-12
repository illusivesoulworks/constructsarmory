package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TraitAmbitious extends AbstractArmorTrait {

    public TraitAmbitious() {
        super("ambitious", 0xffffff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onXPPickUp(PlayerPickupXpEvent evt) {
        int xpValue = evt.getOrb().getXpValue();
        if (xpValue > 0) {
            int level = (int) ArmorHelper.getArmorAbilityLevel(evt.getEntityPlayer(), this.identifier);
            int addXP = random.nextInt(level + 1);
            if (addXP > 0) {
                evt.getEntityPlayer().addExperience(addXP);
            }
        }
    }
}
