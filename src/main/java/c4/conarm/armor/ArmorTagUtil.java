package c4.conarm.armor;

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
