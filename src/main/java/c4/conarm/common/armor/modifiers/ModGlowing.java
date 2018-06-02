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

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.TinkerCommons;

public class ModGlowing extends ArmorModifierTrait {

    public ModGlowing() {
        super("glowing", 0xffffaa);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        if(!world.isRemote) {

            BlockPos pos = player.getPosition();

            if(world.getLightFromNeighbors(pos) < 8) {
                for(BlockPos candidate : new BlockPos[]{pos, pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down()}) {

                    if(TinkerCommons.blockGlow.addGlow(world, candidate, EnumFacing.values()[random.nextInt(6)])) {
                        ArmorHelper.damageArmor(armor, DamageSource.causePlayerDamage(player), 1, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                        return;
                    }
                }
            }
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET && super.canApplyCustom(stack);
    }
}
