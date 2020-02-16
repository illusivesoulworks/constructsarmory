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
