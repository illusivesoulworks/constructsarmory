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
