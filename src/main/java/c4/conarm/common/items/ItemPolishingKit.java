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
                if(!Config.listAllMaterials) {
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
