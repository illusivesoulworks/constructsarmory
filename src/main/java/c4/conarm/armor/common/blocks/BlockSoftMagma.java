package c4.conarm.armor.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSoftMagma extends BlockMagma
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

    public BlockSoftMagma()
    {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
        this.setLightLevel(0.2F);
        this.setTickRandomly(true);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(AGE));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, MathHelper.clamp(meta, 0, 3));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if ((rand.nextInt(3) == 0 || this.countNeighbors(worldIn, pos) < 4))
        {
            this.slightlyMelt(worldIn, pos, state, rand, true);
        }
        else
        {
            worldIn.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (blockIn == this)
        {
            int i = this.countNeighbors(worldIn, pos);

            if (i < 2)
            {
                this.turnIntoLava(worldIn, pos);
            }
        }
    }

    private int countNeighbors(World worldIn, BlockPos pos)
    {
        int i = 0;

        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this)
            {
                ++i;

                if (i >= 4)
                {
                    return i;
                }
            }
        }

        return i;
    }

    protected void slightlyMelt(World worldIn, BlockPos pos, IBlockState state, Random rand, boolean meltNeighbors)
    {
        int i = state.getValue(AGE);

        if (i < 3)
        {
            worldIn.setBlockState(pos, state.withProperty(AGE, i + 1), 2);
            worldIn.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        }
        else
        {
            this.turnIntoLava(worldIn, pos);

            if (meltNeighbors)
            {
                for (EnumFacing enumfacing : EnumFacing.values())
                {
                    BlockPos blockpos = pos.offset(enumfacing);
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);

                    if (iblockstate.getBlock() == this)
                    {
                        this.slightlyMelt(worldIn, blockpos, iblockstate, rand, false);
                    }
                }
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return ItemStack.EMPTY;
    }

    protected void turnIntoLava(World worldIn, BlockPos pos)
    {
        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockState(pos, Blocks.LAVA.getDefaultState());
        worldIn.neighborChanged(pos, Blocks.LAVA, pos);
    }
}
