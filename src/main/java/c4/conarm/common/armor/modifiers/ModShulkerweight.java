/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;

import java.util.List;

public class ModShulkerweight extends ArmorModifierTrait {

    public ModShulkerweight() {
        super("shulkerweight", 0xaaccff, 1, 25);
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
