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

    public ConfigSyncLevelingPacket() {}

    public ConfigSyncLevelingPacket(int minModifiers, int maxLevels, int xp, float levelMult) {
        newArmorMinModifiers = minModifiers;
        maximumLevels = maxLevels;
        baseXP = xp;
        levelMultiplier = levelMult;
    }

    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        ModArmorLeveling.newArmorMinModifiers = newArmorMinModifiers;
        ModArmorLeveling.maximumLevels = maximumLevels;
        ModArmorLeveling.baseXP = baseXP;
        ModArmorLeveling.levelMultiplier = levelMultiplier;
    }

    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Client side only!");
    }

    public void fromBytes(final ByteBuf buf) {
        this.newArmorMinModifiers = buf.readInt();
        this.maximumLevels = buf.readInt();
        this.baseXP = buf.readInt();
        this.levelMultiplier = buf.readFloat();
    }

    public void toBytes(final ByteBuf buf) {
        buf.writeInt(newArmorMinModifiers);
        buf.writeInt(maximumLevels);
        buf.writeInt(baseXP);
        buf.writeFloat(levelMultiplier);
    }
}
