package c4.conarm.integrations.crafttweaker.actions;

import c4.conarm.integrations.crafttweaker.materials.IConArmMaterial;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import crafttweaker.IAction;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetArmorAction implements IAction {

    private final IConArmMaterial material;
    private final float[] newValue;

    public SetArmorAction(IConArmMaterial material, float[] newValue) {
        this.material = material;
        this.newValue = newValue;
    }

    private static void set(Material material, float[] armor) {
        CoreMaterialStats oldStats = material.getStats(ArmorMaterialType.CORE);
        CoreMaterialStats newCore = new CoreMaterialStats(oldStats.durability, armor[0], armor[1], armor[2], armor[3]);
        material.addStats(newCore);
    }

    @Override
    public void apply() {
        set((Material) material.getInternal(), newValue);
    }


    @Override
    public String describe() {

        String desc = "Setting armor values of " + material.getName() + " to " + "\n";
        StringBuilder sb = new StringBuilder();
        sb.append(desc);
        for (int i = 0; i < newValue.length; i++) {

            if (i > 3) {
                break;
            }

            EntityEquipmentSlot slot;
            switch (i) {
                case 0: slot = EntityEquipmentSlot.FEET; break;
                case 1: slot = EntityEquipmentSlot.LEGS; break;
                case 2: slot = EntityEquipmentSlot.CHEST; break;
                case 3: slot = EntityEquipmentSlot.HEAD; break;
                default: slot = EntityEquipmentSlot.HEAD; break;
            }
            String s = "\t" + newValue[i] + " for " + slot.getName();
            sb.append(s);
        }
        return sb.toString();
    }
}
