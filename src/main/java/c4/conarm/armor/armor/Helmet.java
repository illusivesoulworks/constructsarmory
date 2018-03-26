package c4.conarm.armor.armor;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class Helmet extends ArmorCore {

    public Helmet(String appearanceName) {
        super(EntityEquipmentSlot.HEAD, appearanceName, ArmorMaterialType.core(ConstructsArmor.helmetCore));
    }

}
