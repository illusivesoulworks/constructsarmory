/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
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
