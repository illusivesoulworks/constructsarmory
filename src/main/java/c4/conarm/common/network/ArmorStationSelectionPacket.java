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

import c4.conarm.client.gui.GuiArmorStation;
import c4.conarm.common.inventory.ContainerArmorStation;
import c4.conarm.lib.armor.ArmorCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.mantle.network.AbstractPacketThreadsafe;
import slimeknights.tconstruct.common.TinkerNetwork;

public class ArmorStationSelectionPacket extends AbstractPacketThreadsafe {

    public ArmorCore armor;
    public int activeSlots;

    public ArmorStationSelectionPacket() {
    }

    public ArmorStationSelectionPacket(final ArmorCore armor, final int activeSlots) {
        this.armor = armor;
        this.activeSlots = activeSlots;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.getMinecraft().player.openContainer;
        if (container instanceof ContainerArmorStation) {
            ((ContainerArmorStation)container).setArmorSelection(this.armor, this.activeSlots);
            if (Minecraft.getMinecraft().currentScreen instanceof GuiArmorStation) {
                ((GuiArmorStation)Minecraft.getMinecraft().currentScreen).onArmorSelectionPacket(this);
            }
        }
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.player.openContainer;
        if (container instanceof ContainerArmorStation) {
            ((ContainerArmorStation)container).setArmorSelection(this.armor, this.activeSlots);
            final WorldServer server = netHandler.player.getServerWorld();
            for (final EntityPlayer player : server.playerEntities) {
                if (player == netHandler.player) {
                    continue;
                }
                if (!(player.openContainer instanceof ContainerArmorStation) || !((BaseContainer)container).sameGui((BaseContainer)player.openContainer)) {
                    continue;
                }
                ((ContainerArmorStation)player.openContainer).setArmorSelection(this.armor, this.activeSlots);
                TinkerNetwork.sendTo(this, (EntityPlayerMP)player);
            }
        }
    }

    public void fromBytes(final ByteBuf buf) {
        final int id = buf.readShort();
        if (id > -1) {
            final Item item = Item.getItemById(id);
            if (item instanceof ArmorCore) {
                this.armor = (ArmorCore)item;
            }
        }
        this.activeSlots = buf.readInt();
    }

    public void toBytes(final ByteBuf buf) {
        if (this.armor == null) {
            buf.writeShort(-1);
        }
        else {
            buf.writeShort(Item.getIdFromItem(this.armor));
        }
        buf.writeInt(this.activeSlots);
    }
}
