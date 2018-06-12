/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.blocks;

import c4.conarm.common.tileentities.TileArmorStation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.shared.block.BlockTable;
import slimeknights.tconstruct.tools.common.block.ITinkerStationBlock;

import javax.annotation.Nonnull;

public class BlockArmorStation extends BlockTable implements ITinkerStationBlock {

    public BlockArmorStation() {
        super(Material.WOOD);
        this.setCreativeTab(TinkerRegistry.tabGeneral);
        this.setSoundType(SoundType.WOOD);
        this.setResistance(5f);
        this.setHardness(1f);
        this.setHarvestLevel("axe", 0);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileArmorStation();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{}, new IUnlistedProperty[]{TEXTURE, INVENTORY, FACING});
    }

    @Override
    public boolean openGui(EntityPlayer player, World world, BlockPos pos) {
        if(!world.isRemote) {
            player.openGui(TConstruct.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            if(player.openContainer instanceof BaseContainer) {
                ((BaseContainer) player.openContainer).syncOnOpen((EntityPlayerMP) player);
            }
        }
        return true;
    }

    @Override
    public int getGuiNumber(IBlockState state) {
        return 30;
    }
}
