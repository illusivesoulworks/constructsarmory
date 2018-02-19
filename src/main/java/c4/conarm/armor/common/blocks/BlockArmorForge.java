package c4.conarm.armor.common.blocks;

import c4.conarm.armor.common.tileentities.TileArmorForge;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimeknights.tconstruct.tools.common.block.BlockToolForge;

import javax.annotation.Nonnull;

public class BlockArmorForge extends BlockToolForge {

    public BlockArmorForge() {
        super();
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileArmorForge();
    }

    @Override
    public int getGuiNumber(IBlockState state) {
        return 30;
    }
}
