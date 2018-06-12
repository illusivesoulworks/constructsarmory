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

package c4.conarm.common.tileentities;

import c4.conarm.client.gui.GuiButtonArmorRepair;
import c4.conarm.common.inventory.ContainerArmorStation;
import c4.conarm.client.gui.GuiArmorStation;
import c4.conarm.lib.client.ArmorBuildGuiInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.shared.block.BlockTable;
import slimeknights.tconstruct.shared.block.PropertyTableItem;
import slimeknights.tconstruct.tools.common.client.GuiButtonRepair;
import slimeknights.tconstruct.tools.common.tileentity.TileToolStation;

public class TileArmorStation extends TileToolStation {

    public TileArmorStation() {
        inventoryTitle = "gui.armorstation.name";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
        return new GuiArmorStation(inventoryPlayer, world, pos, this);
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
        return new ContainerArmorStation(inventoryPlayer, this, false);
    }
}
