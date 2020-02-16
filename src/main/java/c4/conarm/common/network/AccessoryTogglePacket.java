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

package c4.conarm.common.network;

import c4.conarm.lib.modifiers.AccessoryModifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetHandlerPlayServer;
import slimeknights.mantle.network.AbstractPacketThreadsafe;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class AccessoryTogglePacket extends AbstractPacketThreadsafe {

    public int slot;

    public AccessoryTogglePacket() {}

    public AccessoryTogglePacket(int slotIn) {
        this.slot = slotIn;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        throw new UnsupportedOperationException("Server side only!");
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        EntityEquipmentSlot actualSlot = getSlotFromIndex(this.slot);
        if (actualSlot != null) {
            ItemStack stack = netHandler.player.getItemStackFromSlot(actualSlot);
            if (!ToolHelper.isBroken(stack)) {
                NBTTagList list = TagUtil.getTraitsTagList(stack);
                for (int i = 0; i < list.tagCount(); i++) {
                    ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                    if (trait != null && trait instanceof AccessoryModifier) {
                        ((AccessoryModifier) trait).onKeybinding(stack, netHandler.player);
                        break;
                    }
                }
            }
        }
    }

    public void fromBytes(final ByteBuf buf) {
        this.slot = buf.readInt();
    }

    public void toBytes(final ByteBuf buf) {
        buf.writeInt(slot);
    }

    private EntityEquipmentSlot getSlotFromIndex(int slot) {

        switch (slot) {
            case 0: return EntityEquipmentSlot.FEET;
            case 1: return EntityEquipmentSlot.LEGS;
            case 2: return EntityEquipmentSlot.CHEST;
            case 3: return EntityEquipmentSlot.HEAD;
        }

        return null;
    }
}
