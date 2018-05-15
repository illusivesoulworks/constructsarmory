/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.network;

import c4.conarm.common.inventory.ContainerArmorStation;
import c4.conarm.client.gui.GuiArmorStation;
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

/*This class is a re-implementation of the
ToolStationSelectionPacket class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
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
