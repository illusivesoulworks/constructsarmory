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
