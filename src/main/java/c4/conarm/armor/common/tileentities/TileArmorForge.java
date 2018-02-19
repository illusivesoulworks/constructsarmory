package c4.conarm.armor.common.tileentities;

import c4.conarm.armor.common.inventory.ContainerArmorForge;
import c4.conarm.client.GuiArmorForge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.tools.common.tileentity.TileToolStation;

public class TileArmorForge extends TileToolStation {

    public TileArmorForge() {
        inventoryTitle = "gui.armorforge.name";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
        return new GuiArmorForge(inventoryPlayer, world, pos, this);
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, World world, BlockPos pos) {
        return new ContainerArmorForge(inventoryPlayer, this);
    }
}
