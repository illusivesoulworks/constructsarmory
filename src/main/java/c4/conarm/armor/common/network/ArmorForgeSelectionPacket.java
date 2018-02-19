package c4.conarm.armor.common.network;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.armor.common.inventory.ContainerArmorForge;
import c4.conarm.client.GuiArmorForge;
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
public class ArmorForgeSelectionPacket extends AbstractPacketThreadsafe {

    public ArmorCore armor;
    public int activeSlots;

    public ArmorForgeSelectionPacket() {
    }

    public ArmorForgeSelectionPacket(final ArmorCore armor, final int activeSlots) {
        this.armor = armor;
        this.activeSlots = activeSlots;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.getMinecraft().player.openContainer;
        if (container instanceof ContainerArmorForge) {
            ((ContainerArmorForge)container).setArmorSelection(this.armor, this.activeSlots);
            if (Minecraft.getMinecraft().currentScreen instanceof GuiArmorForge) {
                ((GuiArmorForge)Minecraft.getMinecraft().currentScreen).onArmorSelectionPacket(this);
            }
        }
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.player.openContainer;
        if (container instanceof ContainerArmorForge) {
            ((ContainerArmorForge)container).setArmorSelection(this.armor, this.activeSlots);
            final WorldServer server = netHandler.player.getServerWorld();
            for (final EntityPlayer player : server.playerEntities) {
                if (player == netHandler.player) {
                    continue;
                }
                if (!(player.openContainer instanceof ContainerArmorForge) || !((BaseContainer)container).sameGui((BaseContainer)player.openContainer)) {
                    continue;
                }
                ((ContainerArmorForge)player.openContainer).setArmorSelection(this.armor, this.activeSlots);
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
