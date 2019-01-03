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
