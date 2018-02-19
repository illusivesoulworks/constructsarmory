package c4.conarm.client;

import c4.conarm.lib.client.ArmorBuildGuiInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.translation.I18n;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.Icons;
import slimeknights.tconstruct.tools.common.client.GuiButtonItem;

/*This class is a re-implementation of the
GuiButtonRepair class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
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
