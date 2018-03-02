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

public class ItemTravelSack extends AccessoryBase {

    public ItemTravelSack() {
        super(EntityEquipmentSlot.CHEST);
    }
}
