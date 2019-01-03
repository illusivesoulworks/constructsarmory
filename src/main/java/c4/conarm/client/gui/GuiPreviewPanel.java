/*
 * Copyright (c) 2018-2019 <C4>
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
