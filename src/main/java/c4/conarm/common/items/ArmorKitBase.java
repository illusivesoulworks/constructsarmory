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
