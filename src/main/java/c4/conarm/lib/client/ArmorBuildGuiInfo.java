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
