/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.client.gui;

import c4.conarm.common.inventory.ContainerKnapsack;
import c4.conarm.common.armor.modifiers.ModTravelSack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GuiContainerKnapsack extends GuiContainer {

    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("minecraft","textures/gui/container/generic_54.png");

    private final ContainerKnapsack container;
    private final IInventory player;
    private final int inventoryRows;

    public GuiContainerKnapsack(@Nonnull ContainerKnapsack containerKnapsack, IInventory playerInventory) {

        super(containerKnapsack);

        this.container = containerKnapsack;
        this.player = playerInventory;
        this.inventoryRows = ModTravelSack.SACK_SIZE / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.container.getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

}
