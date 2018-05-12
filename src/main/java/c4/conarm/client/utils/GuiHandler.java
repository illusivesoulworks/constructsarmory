package c4.conarm.client.utils;

import c4.conarm.common.inventory.ContainerKnapsack;
import c4.conarm.client.gui.GuiContainerKnapsack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_KNAPSACK_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == GUI_KNAPSACK_ID) {
            return new ContainerKnapsack(player.inventory, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == GUI_KNAPSACK_ID) {
            return new GuiContainerKnapsack(new ContainerKnapsack(player.inventory, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST)), player.inventory);
        }

        return null;
    }
}
