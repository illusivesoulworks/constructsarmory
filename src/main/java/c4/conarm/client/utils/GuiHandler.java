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

package c4.conarm.client.utils;

import c4.conarm.client.gui.GuiContainerKnapsack;
import c4.conarm.client.gui.GuiContainerPotionBelt;
import c4.conarm.common.inventory.ContainerKnapsack;
import c4.conarm.common.inventory.ContainerPotionBelt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_KNAPSACK_ID = 0;
    public static final int GUI_POTIONBELT_ID = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case GUI_KNAPSACK_ID:
                return new ContainerKnapsack(player.inventory, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
            case GUI_POTIONBELT_ID:
                return new ContainerPotionBelt(player.inventory, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case GUI_KNAPSACK_ID:
                return new GuiContainerKnapsack(new ContainerKnapsack(player.inventory, player.getItemStackFromSlot(EntityEquipmentSlot.CHEST)), player.inventory);
            case GUI_POTIONBELT_ID:
                return new GuiContainerPotionBelt(new ContainerPotionBelt(player.inventory, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS)), player.inventory);
        }

        return null;
    }
}
