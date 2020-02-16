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

import c4.conarm.lib.client.ArmorBuildGuiInfo;
import net.minecraft.client.Minecraft;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.Icons;
import slimeknights.tconstruct.tools.common.client.GuiButtonItem;

public class GuiButtonArmorRepair extends GuiButtonItem<ArmorBuildGuiInfo> {

    public static final ArmorBuildGuiInfo info;

    public GuiButtonArmorRepair(int buttonId, int x, int y) {
        super(buttonId, x, y, Util.translate("gui.repair"), info);
    }

    @Override
    protected void drawIcon(Minecraft mc) {
        mc.getTextureManager().bindTexture(Icons.ICON);
        Icons.ICON_Anvil.draw(x, y);
    }

    static {
        int x = 7 + 80 / 2 - 8 - 6;
        int y = 18 + 64 / 2 - 8;

        info = new ArmorBuildGuiInfo();

        info.addSlotPosition(x, y);

        info.addSlotPosition(x - 18, y + 20);
        info.addSlotPosition(x - 22, y - 5);
        info.addSlotPosition(x, y - 23);
        info.addSlotPosition(x + 22, y - 5);
        info.addSlotPosition(x + 18, y + 20);
    }
}
