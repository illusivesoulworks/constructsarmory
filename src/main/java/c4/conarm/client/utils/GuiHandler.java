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
