package c4.conarm.armor.common.network;

import c4.conarm.armor.common.inventory.ContainerArmorForge;
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
public class ArmorForgeTextPacket extends AbstractPacketThreadsafe {

    public String text;

    public ArmorForgeTextPacket() {
    }

    public ArmorForgeTextPacket(final String text) {
        this.text = text;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.getMinecraft().player.openContainer;
        if (container instanceof ContainerArmorForge) {
            ((ContainerArmorForge)container).setArmorName(this.text);
        }
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.player.openContainer;
        if (container instanceof ContainerArmorForge) {
            ((ContainerArmorForge)container).setArmorName(this.text);
            final WorldServer server = netHandler.player.getServerWorld();
            for (final EntityPlayer player : server.playerEntities) {
                if (player.openContainer instanceof ContainerArmorForge && ((ContainerArmorForge)container).sameGui((BaseContainer)player.openContainer)) {
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
