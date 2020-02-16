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

package c4.conarm.common.armor.utils;

import c4.conarm.lib.armor.ArmorNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;

public class ArmorTagUtil {

    public static final String DEFENSE = "Defense";
    public static final String TOUGHNESS = "Toughness";

    public static ArmorNBT getArmorStats(ItemStack stack) {
        return getArmorStats(TagUtil.getTagSafe(stack));
    }

    public static ArmorNBT getArmorStats(NBTTagCompound root) {
        return new ArmorNBT(TagUtil.getToolTag(root));
    }

    public static ArmorNBT getOriginalArmorStats(ItemStack stack) {
        return getOriginalArmorStats(TagUtil.getTagSafe(stack));
    }

    public static ArmorNBT getOriginalArmorStats(NBTTagCompound root) {
        return new ArmorNBT(TagUtil.getTagSafe(root, Tags.TOOL_DATA_ORIG));
    }
}
