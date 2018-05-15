/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.items;

import slimeknights.tconstruct.library.TinkerRegistry;

public class ArmorKitBase extends ItemBase {

    public ArmorKitBase() {
        this.setCreativeTab(TinkerRegistry.tabGeneral);
    }

//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        tooltip.addAll(LocUtils.getTooltips(Util.translate(String.format("%s.tooltip", this.getUnlocalizedName()))));
//    }
}
