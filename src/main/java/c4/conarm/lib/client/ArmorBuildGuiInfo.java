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

package c4.conarm.lib.client;

import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.lwjgl.util.Point;

import javax.annotation.Nonnull;
import java.util.List;

public class ArmorBuildGuiInfo {

    @Nonnull
    public final ItemStack armor;
    public final List<Point> positions = Lists.newArrayList();

    public ArmorBuildGuiInfo() {
        this.armor = ItemStack.EMPTY;
    }

    public ArmorBuildGuiInfo(@Nonnull TinkersArmor armor) {
        this.armor = armor.buildItemForRenderingInGui();
    }

    public static ArmorBuildGuiInfo default3Part(@Nonnull TinkersArmor armor) {
        ArmorBuildGuiInfo info = new ArmorBuildGuiInfo(armor);
        info.addSlotPosition(29, 42 + 10);
        info.addSlotPosition(29 - 20, 42 - 10);
        info.addSlotPosition(29 + 20, 42 - 10);
        return info;
    }

    public void addSlotPosition(int x, int y) {
        positions.add(new Point(x, y));
    }

}
