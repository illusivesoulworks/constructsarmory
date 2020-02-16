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

import c4.conarm.lib.ArmoryRegistryClient;
import c4.conarm.lib.client.ArmorBuildGuiInfo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.client.Icons;
import slimeknights.tconstruct.tools.common.client.GuiButtonItem;
import slimeknights.tconstruct.tools.common.client.module.GuiSideButtons;

import java.io.IOException;

public class GuiButtonsArmorStation extends GuiSideButtons {

    protected final GuiArmorStation parent;

    public GuiButtonsArmorStation(GuiArmorStation parent, Container container, int columns) {
        super(parent, container, columns);

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
