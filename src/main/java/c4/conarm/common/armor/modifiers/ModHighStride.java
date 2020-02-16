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

import c4.conarm.common.network.SetStepHeightPacket;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModHighStride extends ArmorModifierTrait {

    public ModHighStride() {
        super("high_stride", 0x8c8c8c, 2, 0);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        NBTTagCompound tag = TinkerUtil.getModifierTag(armor, identifier);
        ModifierNBT data = ModifierNBT.readTag(tag);
        if (player instanceof EntityPlayerMP) {
            float stepHeight = 0.6F;
            if (!player.isSneaking()) {
                stepHeight += data.level;
            }
            TinkerNetwork.sendTo(new SetStepHeightPacket(stepHeight), (EntityPlayerMP) player);
        }
    }

    @Override
    public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {
        if (player instanceof EntityPlayerMP) {
            TinkerNetwork.sendTo(new SetStepHeightPacket(0.6F), (EntityPlayerMP) player);
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET && super.canApplyCustom(stack);
    }
}
