package c4.conarm.armor.common.network;

import slimeknights.tconstruct.common.TinkerNetwork;

public class ConstructsNetwork {

    public static void init() {
        TinkerNetwork.instance.registerPacket(ArmorForgeSelectionPacket.class);
        TinkerNetwork.instance.registerPacket(ArmorForgeTextPacket.class);
    }
}
