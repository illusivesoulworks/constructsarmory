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
