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

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IToggleable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public abstract class AbstractToggleAccessoryModifier extends AccessoryModifier implements IToggleable {

    private static final String TAG_TOGGLE = "toggle";

    protected boolean damagesArmor;

    public AbstractToggleAccessoryModifier(String identifier, boolean damagesArmor) {
        super(identifier);
        this.damagesArmor = damagesArmor;
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (!world.isRemote && damagesArmor && player.ticksExisted % 100 == 0 && getToggleData(armor).toggle) {
            ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 1, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        }
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        toggle(armor, player);
    }

    private void toggle(ItemStack stack, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        ToggleData data = modtag.getTagData(ToggleData.class);
        if (!data.toggle && damagesArmor) {
            ArmorHelper.damageArmor(stack, DamageSource.GENERIC, 3, player, EntityLiving.getSlotForItemStack(stack).getIndex());
        }
        data.toggle = !data.toggle;
        modtag.save();
    }

    protected ToggleData getToggleData(ItemStack stack) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        return modtag.getTagData(ToggleData.class);
    }

    @Override
    public boolean getToggleStatus(ItemStack stack) {
        return getToggleData(stack).toggle;
    }

    public static class ToggleData extends ModifierNBT {

        public boolean toggle = false;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            toggle = tag.getBoolean(TAG_TOGGLE);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setBoolean(TAG_TOGGLE, toggle);
        }
    }
}
