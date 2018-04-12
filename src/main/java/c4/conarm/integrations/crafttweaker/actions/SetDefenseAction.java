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

public class SetDefenseAction implements IAction {

    private final IConArmMaterial material;
    private final float newValue;

    public SetDefenseAction(IConArmMaterial material, float newValue) {
        this.material = material;
        this.newValue = newValue;
    }

    private static void set(Material material, float defense) {
        CoreMaterialStats oldStats = material.getStats(ArmorMaterialType.CORE);
        CoreMaterialStats newCore = new CoreMaterialStats(oldStats.durability, defense);
        material.addStats(newCore);
    }

    @Override
    public void apply() {
        set((Material) material.getInternal(), newValue);
    }


    @Override
    public String describe() {
        return "Setting defense of " + material.getName() + " to " + newValue;
    }
}
