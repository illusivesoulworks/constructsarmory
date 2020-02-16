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

package c4.conarm.lib.events;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import slimeknights.tconstruct.library.materials.Material;

public abstract class ArmoryEvent extends Event {

    /**
     * Fired when an armor piece is built.
     * This happens every time an armor piece is loaded as well as when the player actually builds the armor piece.
     * You can make changes to the tag compound and it'll land on the resulting armor, but its itemstack is not available.
     */
    public static class OnItemBuilding extends ArmoryEvent {

        public NBTTagCompound tag;
        public final ImmutableList<Material> materials;
        public final TinkersArmor armor;

        public OnItemBuilding(NBTTagCompound tag, ImmutableList<Material> materials, TinkersArmor armor) {
            this.tag = tag;
            this.materials = materials;
            this.armor = armor;
        }

        public static OnItemBuilding fireEvent(NBTTagCompound tag, ImmutableList<Material> materials, TinkersArmor armor) {
            OnItemBuilding event = new OnItemBuilding(tag, materials, armor);
            MinecraftForge.EVENT_BUS.post(event);
            return event;
        }
    }

    public static class OnRepair extends ArmoryEvent {

        public final int amount;
        public final ItemStack itemStack;
        public final ArmorCore armor;

        public OnRepair(ItemStack itemStack, int amount) {
            this.itemStack = itemStack;
            this.armor = (ArmorCore) itemStack.getItem();
            this.amount = amount;
        }

        public static boolean fireEvent(ItemStack itemStack, int amount) {
            OnRepair event = new OnRepair(itemStack, amount);
            return !MinecraftForge.EVENT_BUS.post(event);
        }
    }
}