/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.traits;

import c4.conarm.lib.utils.ConstructUtils;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.potion.TinkerPotion;

import javax.annotation.Nonnull;

public class TraitAquaspeed extends AbstractArmorTrait {

    public static TinkerPotion aquaspeedPotion = new AquaspeedPotion();

    private static final double SPEED_MOD = 0.05D;

    public TraitAquaspeed() {
        super("aquaspeed", TextFormatting.AQUA);
    }

    /*Code derived from Vazkii's Botania Water Ring*/
    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        if (!world.isRemote && (player.isInWater())) {
            aquaspeedPotion.apply(player, 5, level);
        }
    }

    private static class AquaspeedPotion extends TinkerPotion {

        AquaspeedPotion() {
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
                double maxSpeed = 1.1D + (level - 1) / 15D;

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