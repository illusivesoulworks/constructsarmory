package c4.conarm.common.network;

import slimeknights.tconstruct.common.TinkerNetwork;

public class ConstructsNetwork {

    public static void init() {
        TinkerNetwork.instance.registerPacket(ArmorStationSelectionPacket.class);
        TinkerNetwork.instance.registerPacket(ArmorStationTextPacket.class);
        TinkerNetwork.instance.registerPacketServer(AccessoryTogglePacket.class);
    }
}
