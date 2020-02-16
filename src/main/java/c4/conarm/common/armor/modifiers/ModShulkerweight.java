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

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModShulkerweight extends ArmorModifierTrait {

    protected int max;

    public ModShulkerweight(int count) {
        super("shulkerweight", 0xaaccff, 1, count);
        this.max = count;
    }

    protected float getJumpBonus(ItemStack stack) {
        NBTTagCompound modifierTag = new NBTTagCompound();
        NBTTagList tagList = TagUtil.getModifiersTagList(TagUtil.getTagSafe(stack));
        int index = TinkerUtil.getIndexInList(tagList, identifier);
        if(index >= 0) {
            modifierTag = tagList.getCompoundTagAt(index);
        }
        ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);
        return getJumpBonus(modData);
    }

    protected float getJumpBonus(ModifierNBT.IntegerNBT modData) {
        return 0.4F * modData.current / this.max;
    }

    @Override
    public void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt) {
        player.motionY += getJumpBonus(armor);
    }
}
