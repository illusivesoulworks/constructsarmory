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
