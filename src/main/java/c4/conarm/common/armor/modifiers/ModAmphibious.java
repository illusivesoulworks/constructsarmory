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
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import java.util.List;

public class ModAmphibious extends ArmorModifierTrait {

    private static final String TAG_OXYGEN = "oxygen";
    private static final int MAX_CAPACITY = 1200;

    public ModAmphibious() {
        super("amphibious", 0x00ccff);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (!world.isRemote) {
            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
            OxygenData data = modtag.getTagData(OxygenData.class);

            if (player.isInWater()) {
                if (player.getAir() < 300 && data.oxygen > 0) {
                    addOxygen(modtag, data, -1);
                    player.setAir(player.getAir() + 1);
                }
            } else if (canStoreOxygen(data)) {
                addOxygen(modtag, data, 2);
            }
        }
    }

    private boolean canStoreOxygen(OxygenData data) {
        return data.oxygen < MAX_CAPACITY;
    }

    private void addOxygen(ModifierTagHolder modtag, OxygenData data, int amount) {
        data.oxygen = MathHelper.clamp(data.oxygen + amount, 0, MAX_CAPACITY);
        modtag.save();
    }

    @Override
    public List<String> getExtraInfo(ItemStack armor, NBTTagCompound modifierTag) {
        OxygenData data = ModifierNBT.readTag(modifierTag, OxygenData.class);
        assert data != null;
        String loc = String.format(LOC_Extra, getIdentifier());
        return ImmutableList.of(Util.translateFormatted(loc, data.oxygen / 20));
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && super.canApplyCustom(stack);
    }

    public static class OxygenData extends ModifierNBT {

        public int oxygen = 0;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            oxygen = tag.getInteger(TAG_OXYGEN);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setInteger(TAG_OXYGEN, oxygen);
        }
    }
}
