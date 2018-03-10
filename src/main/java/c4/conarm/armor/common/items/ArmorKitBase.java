package c4.conarm.armor.common.items;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.PlatesMaterialStats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.mantle.util.LocUtils;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nullable;
import java.util.List;

public class ArmorKitBase extends ItemBase {

    public ArmorKitBase() {
        this.setCreativeTab(TinkerRegistry.tabGeneral);
    }

//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        tooltip.addAll(LocUtils.getTooltips(Util.translate(String.format("%s.tooltip", this.getUnlocalizedName()))));
//    }
}
