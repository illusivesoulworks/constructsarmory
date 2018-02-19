package c4.conarm.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;

import java.util.List;

public class ModLowGravity extends ArmorModifierTrait {

    public ModLowGravity() {
        super("low_gravity", 0xaaccff, 1, 50);
    }

    private int getAmplifier(ItemStack armor) {
        return getData(armor).current;
    }

//    @Override
//    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
//
//        player.setNoGravity(true);
//
//        if (player.ticksExisted % 2 == 0) {
//            player.setNoGravity(false);
//        }
//    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getIdentifier());
        float amplifier = getAmplifier(tool);
        amplifier /= 100f;

        return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(amplifier)));
    }
}
