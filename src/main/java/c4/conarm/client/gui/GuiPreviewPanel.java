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

package c4.conarm.client.gui;

import c4.conarm.common.inventory.ContainerArmorStation;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.gui.GuiMultiModule;
import slimeknights.tconstruct.tools.common.client.module.GuiInfoPanel;

public class GuiPreviewPanel extends GuiInfoPanel {

    ContainerArmorStation container;
    float oldMouseX;
    float oldMouseY;
    PreviewPlayer previewPlayer;

    public GuiPreviewPanel(GuiMultiModule parent, Container container, int xSize, int ySize, PreviewPlayer previewPlayer) {
        super(parent, container);
        this.container = (ContainerArmorStation) container;
        this.xSize = xSize;
        this.ySize = ySize;
        this.previewPlayer = previewPlayer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        int i = this.guiLeft;
        int j = this.guiTop;

        GuiInventory.drawEntityOnScreen(i + 53, j + 135, 60, (float)(i + 50) - this.oldMouseX, (float)(j + 30) - this.oldMouseY, this.previewPlayer);

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    protected void givePreviewStack(ItemStack stack) {
        previewPlayer.setItemStackToSlot(EntityLiving.getSlotForItemStack(stack), stack.copy());
    }

    protected void resetPreview() {
        previewPlayer.inventory.clear();
    }
}
