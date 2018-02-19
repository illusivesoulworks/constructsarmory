package c4.conarm.lib;

import c4.conarm.armor.ConstructsArmor;
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
        ArmorBuildGuiInfo info;

        info = new ArmorBuildGuiInfo(ConstructsArmor.helmet);
        info.addSlotPosition(29, 42 + 10);
        info.addSlotPosition(29 - 20, 42 - 10);
        info.addSlotPosition(29 + 20, 42 - 10);
        addArmorBuilding(info);

        info = new ArmorBuildGuiInfo(ConstructsArmor.chestplate);
        info.addSlotPosition(29, 42 + 10);
        info.addSlotPosition(29 - 20, 42 - 10);
        info.addSlotPosition(29 + 20, 42 - 10);
        addArmorBuilding(info);

        info = new ArmorBuildGuiInfo(ConstructsArmor.leggings);
        info.addSlotPosition(29, 42 + 10);
        info.addSlotPosition(29 - 20, 42 - 10);
        info.addSlotPosition(29 + 20, 42 - 10);
        addArmorBuilding(info);

        info = new ArmorBuildGuiInfo(ConstructsArmor.boots);
        info.addSlotPosition(29, 42 + 10);
        info.addSlotPosition(29 - 20, 42 - 10);
        info.addSlotPosition(29 + 20, 42 - 10);
        addArmorBuilding(info);
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
