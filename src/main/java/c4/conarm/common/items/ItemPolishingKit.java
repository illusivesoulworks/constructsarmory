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

package c4.conarm.common.items;

import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.PlatesMaterialStats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import slimeknights.mantle.util.LocUtils;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPolishingKit extends ArmorPart {

    public ItemPolishingKit() {
        super(Material.VALUE_Shard * 4);
    }

    @Override
    public boolean canUseMaterial(Material mat) {
        return mat.hasStats(ArmorMaterialType.PLATES);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(this.isInCreativeTab(tab)) {
            // this adds a variant of each material to the creative menu
            for(Material mat : TinkerRegistry.getAllMaterialsWithStats(ArmorMaterialType.PLATES)) {
                subItems.add(getItemstackWithMaterial(mat));
                if(!Config.listAllPartMaterials) {
                    break;
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.addAll(LocUtils.getTooltips(Util.translate("item.conarm.polishing_kit.tooltip")));
        if(!checkMissingMaterialTooltip(stack, tooltip, ArmorMaterialType.PLATES)) {
            Material material = getMaterial(stack);
            PlatesMaterialStats stats = material.getStats(ArmorMaterialType.PLATES);
            if(stats != null) {
                tooltip.add(PlatesMaterialStats.formatToughness(stats.toughness));
            }
        }
    }
}
