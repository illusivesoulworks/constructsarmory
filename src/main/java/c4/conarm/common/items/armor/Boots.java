package c4.conarm.common.items.armor;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class Boots extends ArmorCore {

    public Boots(String appearanceName) {
        super(EntityEquipmentSlot.FEET, appearanceName, ArmorMaterialType.core(ConstructsRegistry.bootsCore));
    }
}
