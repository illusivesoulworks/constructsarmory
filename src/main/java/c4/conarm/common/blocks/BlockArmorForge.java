/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.blocks;

import c4.conarm.common.tileentities.TileArmorForge;
import com.google.common.collect.Sets;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.shared.block.BlockTable;
import slimeknights.tconstruct.tools.common.block.ITinkerStationBlock;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class BlockArmorForge extends BlockTable implements ITinkerStationBlock {

    public final Set<String> baseBlocks = Sets.newLinkedHashSet();

    public BlockArmorForge() {
        super(Material.IRON);
        this.setCreativeTab(TinkerRegistry.tabGeneral);
        this.setSoundType(SoundType.METAL);
        this.setResistance(10f);
        this.setHardness(2f);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean openGui(EntityPlayer player, World world, BlockPos pos) {
        player.openGui(TConstruct.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        if(player.openContainer instanceof BaseContainer) {
            ((BaseContainer) player.openContainer).syncOnOpen((EntityPlayerMP) player);
        }
        return true;
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileArmorForge();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for(String oredict : baseBlocks) {
            List<ItemStack> ores = OreDictionary.getOres(oredict, false);
            if(ores.size() > 0) {
                list.add(createItemstack(this, 0, getBlockFromItem(ores.get(0).getItem()),
                        ores.get(0).getItemDamage()));
                if(!Config.listAllTables) {
                    return;
                }
            }
        }
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{}, new IUnlistedProperty[]{TEXTURE, INVENTORY, FACING});
    }

    @Override
    public int getGuiNumber(IBlockState state) {
        return 30;
    }
}
