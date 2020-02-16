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

import c4.conarm.integrations.tinkertoolleveling.ConfigSyncLevelingPacket;
import slimeknights.tconstruct.common.TinkerNetwork;

public class ConstructsNetwork {

    public static void init() {
        TinkerNetwork.instance.registerPacket(ArmorStationSelectionPacket.class);
        TinkerNetwork.instance.registerPacket(ArmorStationTextPacket.class);
        TinkerNetwork.instance.registerPacketServer(AccessoryTogglePacket.class);
        TinkerNetwork.instance.registerPacketClient(SetStepHeightPacket.class);
        TinkerNetwork.instance.registerPacketClient(ConfigSyncLevelingPacket.class);
        TinkerNetwork.instance.registerPacketServer(ArmorBouncedPacket.class);
    }
}
