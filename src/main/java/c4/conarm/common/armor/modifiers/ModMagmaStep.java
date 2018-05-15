/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.base.Objects;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;

public class ModMagmaStep extends ArmorModifierTrait {

    public ModMagmaStep() {
        super("magma_step", 0x993d00);

        addAspects(new ModifierAspect.LevelAspect(this, 2), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (world.isRemote) {
            return;
        }

        BlockPos prevBlockpos = new BlockPos(player.prevPosX, player.prevPosY, player.prevPosZ);
        BlockPos blockpos = new BlockPos(player);

        if (world.getBlockState(blockpos.down()).getMaterial() == Material.ICE) {
            meltIce(world, blockpos.down());
        }

        if (!Objects.equal(prevBlockpos, blockpos) && player.onGround)
        {
            magmatizeNearby(player, world, blockpos, getData(armor).level);
        }
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return enchantment != Enchantments.FROST_WALKER;
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET;
    }

    private void meltIce(World worldIn, BlockPos pos)
    {
        if (worldIn.provider.doesWaterVaporize())
        {
            worldIn.setBlockToAir(pos);
        }
        else
        {
            worldIn.getBlockState(pos).getBlock().dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
            worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
            worldIn.neighborChanged(pos, Blocks.WATER, pos);
        }
    }

    private void magmatizeNearby(EntityLivingBase living, World world, BlockPos pos, int level) {

        float f = (float)Math.min(16, 2 + level);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f)))
        {
            if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f))
            {
                blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                IBlockState iblockstate = world.getBlockState(blockpos$mutableblockpos);

                if (iblockstate.getMaterial() == Material.AIR)
                {
                    IBlockState iblockstate1 = world.getBlockState(blockpos$mutableblockpos1);

                    if (iblockstate1.getMaterial() == Material.LAVA && iblockstate1.getBlock() == Blocks.LAVA && iblockstate1.getValue(BlockLiquid.LEVEL) == 0 && world.mayPlace(ConstructsRegistry.softMagma, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null))
                    {
                        world.setBlockState(blockpos$mutableblockpos1, ConstructsRegistry.softMagma.getDefaultState());
                        world.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), ConstructsRegistry.softMagma, MathHelper.getInt(living.getRNG(), 60, 120));
                    }
                }
            }
        }
    }
}
