package c4.conarm.integrations.crafttweaker.actions;

import c4.conarm.integrations.crafttweaker.materials.IConArmMaterial;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import crafttweaker.IAction;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetDurabilityAction implements IAction {

    private final IConArmMaterial material;
    private final String stat;
    private final float newValue;

    public SetDurabilityAction(IConArmMaterial material, String stat, float newValue) {
        this.material = material;
        this.stat = stat;
        this.newValue = newValue;
    }

    private static void set(Material material, String stat, float durability) {
        IMaterialStats oldStats = material.getStats(stat);
        if (oldStats instanceof CoreMaterialStats) {
            CoreMaterialStats coreStats = (CoreMaterialStats) oldStats;
            CoreMaterialStats newCore = new CoreMaterialStats(durability, coreStats.armor[0], coreStats.armor[1], coreStats.armor[2], coreStats.armor[3]);
            material.addStats(newCore);
        } else if (oldStats instanceof PlatesMaterialStats) {
            PlatesMaterialStats plateStats = (PlatesMaterialStats) oldStats;
            PlatesMaterialStats newPlates = new PlatesMaterialStats(plateStats.modifier, durability, plateStats.toughness);
            material.addStats(newPlates);
        } else if (oldStats instanceof TrimMaterialStats) {
            TrimMaterialStats newTrim = new TrimMaterialStats(durability);
            material.addStats(newTrim);
        }
    }

    @Override
    public void apply() {
        set((Material) material.getInternal(), stat, newValue);
    }


    @Override
    public String describe() {
        return "Setting durability of " + material.getName() + " to " + newValue + " for " + stat;
    }
}
