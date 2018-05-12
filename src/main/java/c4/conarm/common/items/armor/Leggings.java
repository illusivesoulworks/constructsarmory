package c4.conarm.common.items.armor;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class Leggings extends ArmorCore {

    public Leggings(String appearanceName) {
        super(EntityEquipmentSlot.LEGS, appearanceName, ArmorMaterialType.core(ConstructsRegistry.leggingsCore));
    }
}
