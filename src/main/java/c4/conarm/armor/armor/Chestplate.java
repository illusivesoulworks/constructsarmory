package c4.conarm.armor.armor;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.materials.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class Chestplate extends ArmorCore {

    public Chestplate(String appearanceName) {
        super(EntityEquipmentSlot.CHEST, appearanceName, ArmorMaterialType.core(ConstructsArmor.chestCore));
    }

}
