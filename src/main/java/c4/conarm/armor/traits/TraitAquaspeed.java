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

    private static final double SPEED_MOD = 0.05D;

    public TraitAquaspeed() {
        super("aquaspeed", TextFormatting.AQUA);
    }

    /*Code derived from Vazkii's Botania Water Ring*/
    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        if (!world.isRemote && (player.isInWater())) {
            double motionX = player.motionX * (1 + SPEED_MOD * level);
            double motionY = player.motionY * (1 + SPEED_MOD * level);
            double motionZ = player.motionZ * (1 + SPEED_MOD * level);

            boolean isFlying = player.capabilities.isFlying;
            double maxSpeed = 1.1D + level / 15D;

            if (!isFlying) {
                if (Math.abs(motionX) < maxSpeed) {
                    player.motionX = motionX;
                }
                if (Math.abs(motionY) < maxSpeed) {
                    player.motionY = motionY;
                }
                if (Math.abs(motionZ) < maxSpeed) {
                    player.motionZ = motionZ;
                }
            }
        }
    }
}