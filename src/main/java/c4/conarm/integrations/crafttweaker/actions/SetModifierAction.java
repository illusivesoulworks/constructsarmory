package c4.conarm.integrations.crafttweaker.actions;

import c4.conarm.integrations.crafttweaker.materials.IConArmMaterial;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.PlatesMaterialStats;
import crafttweaker.IAction;
import slimeknights.tconstruct.library.materials.Material;

public class SetModifierAction implements IAction {

    private final IConArmMaterial material;
    private final float newValue;

    public SetModifierAction(IConArmMaterial material, float newValue) {
        this.material = material;
        this.newValue = newValue;
    }

    private static void set(Material material, float modifier) {
        PlatesMaterialStats oldStats = material.getStats(ArmorMaterialType.PLATES);
        PlatesMaterialStats newPlates = new PlatesMaterialStats(modifier, oldStats.durability, oldStats.toughness);
        material.addStats(newPlates);
    }

    @Override
    public void apply() {
        set((Material) material.getInternal(), newValue);
    }


    @Override
    public String describe() {
        return "Setting Modifier of " + material.getName() + " to " + newValue;
    }

}
