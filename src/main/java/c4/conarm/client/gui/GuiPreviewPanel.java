package c4.conarm.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.inventory.Container;
import slimeknights.mantle.client.gui.GuiMultiModule;
import slimeknights.tconstruct.tools.common.client.module.GuiInfoPanel;

public class GuiPreviewPanel extends GuiInfoPanel {

    public GuiPreviewPanel(GuiMultiModule parent, Container container, int xSize, int ySize) {
        super(parent, container);
        this.xSize = xSize;
        this.ySize = ySize;
    }
}
