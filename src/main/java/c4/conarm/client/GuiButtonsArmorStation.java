package c4.conarm.client;

import c4.conarm.lib.ArmoryRegistryClient;
import c4.conarm.lib.client.ArmorBuildGuiInfo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.client.Icons;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.tools.common.client.GuiButtonItem;
import slimeknights.tconstruct.tools.common.client.module.GuiSideButtons;

import java.io.IOException;

/*This class is a re-implementation of the
GuiButtonsToolStation class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class GuiButtonsArmorStation extends GuiSideButtons {

    protected final GuiArmorStation parent;

    public GuiButtonsArmorStation(GuiArmorStation parent, Container container) {
        super(parent, container, GuiArmorStation.Column_Count);

        this.parent = parent;
    }

    protected int selected = 0;

    private int style = 0;

    @Override
    public void updatePosition(int parentX, int parentY, int parentSizeX, int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);

        int index = 0;
        buttonCount = 0;

        {
            GuiButtonItem<ArmorBuildGuiInfo> button = new GuiButtonArmorRepair(index++, -1, -1);
            shiftButton(button, 0, -18 * style);
            addSideButton(button);
        }

        for (Item item : parent.getBuildableItems()) {
            ArmorBuildGuiInfo info = ArmoryRegistryClient.getArmorBuildInfoForArmor(item);
            if (info != null) {
                GuiButtonItem<ArmorBuildGuiInfo> button = new GuiButtonItem<>(index++, -1, -1, info.armor, info);
                shiftButton(button, 0, -18 * style);
                addSideButton(button);

                if (index - 1 == selected) {
                    button.pressed = true;
                }
            }
        }

        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);

        parent.updateGUI();
    }

    public void setSelectedButtonByArmor(ItemStack stack) {
        for (Object o : buttonList) {
            if (o instanceof GuiButtonItem) {
                @SuppressWarnings("unchecked")
                GuiButtonItem<ArmorBuildGuiInfo> btn = (GuiButtonItem<ArmorBuildGuiInfo>) o;
                btn.pressed = ItemStack.areItemStacksEqual(btn.data.armor, stack);
            }
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void actionPerformed(GuiButton button) throws IOException {
        for (Object o : buttonList) {
            if (o instanceof GuiButtonItem) {
                ((GuiButtonItem<ArmorBuildGuiInfo>) o).pressed = false;
            }
        }
        if (button instanceof GuiButtonItem) {
            ((GuiButtonItem<ArmorBuildGuiInfo>) button).pressed = true;
            selected = button.id;

            parent.onArmorSelection(((GuiButtonItem<ArmorBuildGuiInfo>) button).data);
        }
    }

    @SuppressWarnings("unchecked")
    public void wood() {
        for(Object o : buttonList) {
            shiftButton((GuiButtonItem<ArmorBuildGuiInfo>) o, 0, -36);
        }

        style = 2;
    }

    @SuppressWarnings("unchecked")
    public void metal() {
        for (Object o : buttonList) {
            shiftButton((GuiButtonItem<ArmorBuildGuiInfo>) o, 0, -18);
        }

        style = 1;
    }

    protected void shiftButton(GuiButtonItem<ArmorBuildGuiInfo> button, int xd, int yd) {
        button.setGraphics(Icons.ICON_Button.shift(xd, yd),
                Icons.ICON_ButtonHover.shift(xd, yd),
                Icons.ICON_ButtonPressed.shift(xd, yd),
                Icons.ICON);
    }
}
