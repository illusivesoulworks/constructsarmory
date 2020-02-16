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

package c4.conarm.integrations.tinkertoolleveling;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import slimeknights.mantle.network.AbstractPacketThreadsafe;

public class ConfigSyncLevelingPacket extends AbstractPacketThreadsafe {

    public int newArmorMinModifiers;
    public int maximumLevels;
    public int baseXP;
    public float levelMultiplier;
    public int xpGainCap;
    public float damageToXP;

    public ConfigSyncLevelingPacket() {}

    public ConfigSyncLevelingPacket(int minModifiers, int maxLevels, int xp, float levelMult, int xpGainCap, float damageToXP) {
        this.newArmorMinModifiers = minModifiers;
        this.maximumLevels = maxLevels;
        this.baseXP = xp;
        this.levelMultiplier = levelMult;
        this.xpGainCap = xpGainCap;
        this.damageToXP = damageToXP;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        ModArmorLeveling.newArmorMinModifiers = newArmorMinModifiers;
        ModArmorLeveling.maximumLevels = maximumLevels;
        ModArmorLeveling.baseXP = baseXP;
        ModArmorLeveling.levelMultiplier = levelMultiplier;
        ModArmorLeveling.xpGainCap = xpGainCap;
        ModArmorLeveling.damageToXP = damageToXP;
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Client side only!");
    }

    public void fromBytes(final ByteBuf buf) {
        this.newArmorMinModifiers = buf.readInt();
        this.maximumLevels = buf.readInt();
        this.baseXP = buf.readInt();
        this.levelMultiplier = buf.readFloat();
        this.xpGainCap = buf.readInt();
        this.damageToXP = buf.readFloat();
    }

    public void toBytes(final ByteBuf buf) {
        buf.writeInt(newArmorMinModifiers);
        buf.writeInt(maximumLevels);
        buf.writeInt(baseXP);
        buf.writeFloat(levelMultiplier);
        buf.writeInt(xpGainCap);
        buf.writeFloat(damageToXP);
    }
}
