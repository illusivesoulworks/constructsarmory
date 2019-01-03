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

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import slimeknights.mantle.network.AbstractPacketThreadsafe;

public class SetStepHeightPacket extends AbstractPacketThreadsafe {

    private float stepHeight;

    public SetStepHeightPacket() {}

    public SetStepHeightPacket(float stepHeight) { this.stepHeight = stepHeight; }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        Minecraft.getMinecraft().player.stepHeight = stepHeight;
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Client side only!");
    }

    public void fromBytes(final ByteBuf buf) { this.stepHeight = buf.readFloat(); }

    public void toBytes(final ByteBuf buf) { buf.writeFloat(stepHeight); }
}
