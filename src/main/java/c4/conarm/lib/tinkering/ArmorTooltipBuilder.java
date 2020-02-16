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

package c4.conarm.lib.tinkering;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IToggleable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TooltipBuilder;

import java.util.ArrayList;
import java.util.List;

public class ArmorTooltipBuilder {

    public static void addDefense(TooltipBuilder info, ItemStack stack) {

        info.add(CoreMaterialStats.formatDefense(ArmorHelper.getDefense(stack)));
    }

    public static void addToughness(TooltipBuilder info, ItemStack stack) {

        info.add(PlatesMaterialStats.formatToughness(ArmorHelper.getToughness(stack)));
    }

    public static void addModifierTooltips(ItemStack stack, List<String> tooltips) {
        NBTTagList tagList = TagUtil.getModifiersTagList(stack);
        List<String> toAdd = new ArrayList<>();
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            ModifierNBT data = ModifierNBT.readTag(tag);
            IModifier modifier = TinkerRegistry.getModifier(data.identifier);
            if(modifier == null || modifier.isHidden()) {
                continue;
            }
            if(modifier instanceof AccessoryModifier) {
                tooltips.add(data.getColorString() + modifier.getTooltip(tag, false));
                if (modifier instanceof IToggleable) {
                    String key = ((IToggleable) modifier).getToggleStatus(stack) ? "accessory.toggle.active" : "accessory.toggle.inactive";
                    tooltips.add(data.getColorString() + String.format(Util.translate("accessory.toggle.tooltip"), Util.translate(key)));
                }
                tooltips.add("");
                continue;
            }
            toAdd.add(data.getColorString() + modifier.getTooltip(tag, false));
        }
        tooltips.addAll(toAdd);
    }
}
