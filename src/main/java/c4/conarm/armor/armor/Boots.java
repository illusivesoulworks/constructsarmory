package c4.conarm.armor.armor;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class Boots extends ArmorCore {

    public Boots() {
        super(EntityEquipmentSlot.FEET,
                ArmorMaterialType.core(ConstructsArmor.bootsCore),
                ArmorMaterialType.plating(ConstructsArmor.armorPlate),
                ArmorMaterialType.trim(ConstructsArmor.armorTrim));
    }

    @Override
    protected ArmorNBT buildTagData(List<Material> materials) {
        return buildDefaultTag(materials, 0);
    }
}
