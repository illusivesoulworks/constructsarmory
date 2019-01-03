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
