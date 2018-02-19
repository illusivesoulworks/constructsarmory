package c4.conarm.armor.armor;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class Leggings extends ArmorCore {

    public Leggings() {
        super(EntityEquipmentSlot.LEGS,
                ArmorMaterialType.core(ConstructsArmor.leggingsCore),
                ArmorMaterialType.plating(ConstructsArmor.armorPlate),
                ArmorMaterialType.trim(ConstructsArmor.armorTrim));
    }

    @Override
    protected ArmorNBT buildTagData(List<Material> materials) {
        return buildDefaultTag(materials, 1);
    }
}
