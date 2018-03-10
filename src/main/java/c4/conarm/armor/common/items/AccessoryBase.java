package c4.conarm.armor.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.mantle.util.LocUtils;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nullable;
import java.util.List;

public class AccessoryBase extends ItemBase {

    private final EntityEquipmentSlot slot;

    public AccessoryBase(EntityEquipmentSlot slotIn) {
        this.slot = slotIn;
        this.setCreativeTab(TinkerRegistry.tabGadgets);
        this.setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.addAll(LocUtils.getTooltips(Util.translate(String.format("accessory.%s.tooltip", slot.getName()))));
    }
}
