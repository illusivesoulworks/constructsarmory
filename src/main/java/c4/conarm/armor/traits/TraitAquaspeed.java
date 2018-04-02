package c4.conarm.armor.traits;

import c4.conarm.ConstructsArmory;
import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import c4.conarm.lib.traits.IArmorAbility;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.potion.TinkerPotion;

import javax.annotation.Nonnull;

public class TraitAquaspeed extends AbstractArmorTrait {

    public static TinkerPotion aquaspeedPotion = new AquaspeedPotion();

    private static final double SPEED_MOD = 0.05D;
    private static final double MAX_SPEED_PER_LEVEL = 0.09D;

    public TraitAquaspeed() {
        super("aquaspeed", TextFormatting.AQUA);
    }

    /*Code derived from Vazkii's Botania Water Ring*/
    @Override
    public void onAbilityTick(ArmorAbilityHandler.IArmorAbilities abilities, World world, EntityPlayer player) {
        if (!world.isRemote && (player.isInWater())) {
            aquaspeedPotion.apply(player, 5, (int) ArmorHelper.getArmorAbilityLevel(player, this.identifier));
        }
    }

    private static class AquaspeedPotion extends TinkerPotion {

        public AquaspeedPotion() {
            super(ConstructUtils.getResource("aquaspeedPotion"), false, false);
        }

        @Override
        public boolean isReady(int duration, int strength) {
            return true;
        }

        @Override
        public void performEffect(@Nonnull EntityLivingBase entity, int level) {

            if (entity.isInWater()) {
                double motionX = entity.motionX * (1 + SPEED_MOD * level);
                double motionY = entity.motionY * (1 + SPEED_MOD * level);
                double motionZ = entity.motionZ * (1 + SPEED_MOD * level);

                boolean isFlying = entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying;
                double maxSpeed;
                switch (level) {
                    case 1: maxSpeed = 1.2D; break;
                    case 2: maxSpeed = 1.3D; break;
                    case 3: maxSpeed = 1.35D; break;
                    case 4: maxSpeed = 1.375D; break;
                    default: maxSpeed = 1.0D; break;
                }

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
}