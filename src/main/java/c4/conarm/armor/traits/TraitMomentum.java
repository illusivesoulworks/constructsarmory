package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.potion.TinkerPotion;

import javax.annotation.Nonnull;

public class TraitMomentum extends AbstractArmorTrait {

    public static TinkerPotion momentumPotion = new MomentumPotion();
    private static final double SPEED_MOD = 1.1D;
    private static final double MAX_SPEED_PER_LEVEL = 0.2D;

    public TraitMomentum() {
        super("momentum_armor", TextFormatting.BLUE);
    }

    @Override
    public void onAbilityTick(ArmorAbilityHandler.IArmorAbilities abilities, World world, EntityPlayer player) {
        if (player.moveForward == 0 && player.isPotionActive(momentumPotion)) {
            player.removePotionEffect(momentumPotion);
        } else if (player.moveForward > 0) {
            momentumPotion.apply(player, 20, ArmorHelper.getArmorAbilityLevel(player, this.getIdentifier()));
        }
    }

    private static class MomentumPotion extends TinkerPotion {

        public MomentumPotion() {
            super(ConstructUtils.getResource("momentumPotion"), false, false);
        }

        @Override
        public boolean isReady(int duration, int strength) {
            return true;
        }

        @Override
        public void performEffect(@Nonnull EntityLivingBase entity, int level) {
            double motionX = entity.motionX * SPEED_MOD;
            double motionY = entity.motionY * SPEED_MOD;
            double motionZ = entity.motionZ * SPEED_MOD;

            boolean isFlying = entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying;
            double maxSpeed = 1 + MAX_SPEED_PER_LEVEL * level;

            if (!isFlying) {
                if (Math.abs(motionX) < maxSpeed) {
                    entity.motionX = motionX;
                }
                if (Math.abs(motionY) < maxSpeed) {
                    entity.motionY = motionY;
                }
                if (Math.abs(motionZ) < maxSpeed) {
                    entity.motionZ = motionZ;
                }
            }
        }
    }
}
