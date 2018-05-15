/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.items.armor;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.*;
import net.minecraft.inventory.EntityEquipmentSlot;

public class Chestplate extends ArmorCore {

    public Chestplate(String appearanceName) {
        super(EntityEquipmentSlot.CHEST, appearanceName, ArmorMaterialType.core(ConstructsRegistry.chestCore));
    }

}
