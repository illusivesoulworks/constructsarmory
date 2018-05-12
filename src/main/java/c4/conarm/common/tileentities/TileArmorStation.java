package c4.conarm.common.tileentities;

import c4.conarm.common.inventory.ContainerArmorStation;
import c4.conarm.client.gui.GuiArmorStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
        return new ContainerArmorStation(inventoryPlayer, this);
    }
}
