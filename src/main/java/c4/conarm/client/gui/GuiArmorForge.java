package c4.conarm.client.gui;

import c4.conarm.common.tileentities.TileArmorForge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiArmorForge extends GuiArmorStation {

    public GuiArmorForge(InventoryPlayer player, World world, BlockPos pos, TileArmorForge tile) {
        super(player, world, pos, tile);
        metal();
    }
}
