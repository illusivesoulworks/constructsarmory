package c4.conarm.lib;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.client.ArmorBuildGuiInfo;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class ArmoryRegistryClient {

    private static final Map<Item, ArmorBuildGuiInfo> armorBuildInfo = Maps.newLinkedHashMap();

    public static void registerArmorBuildInfo() {

        addArmorBuilding(ArmorBuildGuiInfo.default3Part(ConstructsRegistry.helmet));
        addArmorBuilding(ArmorBuildGuiInfo.default3Part(ConstructsRegistry.chestplate));
        addArmorBuilding(ArmorBuildGuiInfo.default3Part(ConstructsRegistry.leggings));
        addArmorBuilding(ArmorBuildGuiInfo.default3Part(ConstructsRegistry.boots));
    }

    public static void addArmorBuilding(ArmorBuildGuiInfo info) {
        armorBuildInfo.put(info.armor.getItem(), info);
    }

    public static ArmorBuildGuiInfo getArmorBuildInfoForArmor(Item armor) {
        return armorBuildInfo.get(armor);
    }

//    public static void clear() {
//        armorBuildInfo.clear();
//    }
}
