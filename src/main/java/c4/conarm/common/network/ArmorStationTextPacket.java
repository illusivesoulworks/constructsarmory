/*
 * Copyright (c) 2018-2019 <C4>
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
