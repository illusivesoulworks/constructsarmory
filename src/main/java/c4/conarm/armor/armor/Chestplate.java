package c4.conarm.armor.armor;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.materials.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class Chestplate extends ArmorCore {

    public Chestplate() {
        super(EntityEquipmentSlot.CHEST,
                ArmorMaterialType.core(ConstructsArmor.chestCore),
                ArmorMaterialType.plating(ConstructsArmor.armorPlate),
                ArmorMaterialType.trim(ConstructsArmor.armorTrim));
    }

    @Override
    protected ArmorNBT buildTagData(List<Material> materials) {
        return buildDefaultTag(materials, 2);
    }
}
