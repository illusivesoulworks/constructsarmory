/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.network;

import c4.conarm.common.inventory.ContainerArmorStation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.mantle.network.AbstractPacketThreadsafe;
import slimeknights.tconstruct.common.TinkerNetwork;

/*This class is a re-implementation of the
ToolStationTextPacket class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class ArmorStationTextPacket extends AbstractPacketThreadsafe {

    public String text;

    public ArmorStationTextPacket() {
    }

    public ArmorStationTextPacket(final String text) {
        this.text = text;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.getMinecraft().player.openContainer;
        if (container instanceof ContainerArmorStation) {
            ((ContainerArmorStation)container).setArmorName(this.text);
        }
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.player.openContainer;
        if (container instanceof ContainerArmorStation) {
            ((ContainerArmorStation)container).setArmorName(this.text);
            final WorldServer server = netHandler.player.getServerWorld();
            for (final EntityPlayer player : server.playerEntities) {
                if (player.openContainer instanceof ContainerArmorStation && ((ContainerArmorStation)container).sameGui((BaseContainer)player.openContainer)) {
                    TinkerNetwork.sendTo(this, (EntityPlayerMP)player);
                }
            }
        }
    }

    public void fromBytes(final ByteBuf buf) {
        this.text = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.text);
    }
}
