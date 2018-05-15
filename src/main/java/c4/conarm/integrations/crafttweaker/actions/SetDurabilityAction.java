/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

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
            CoreMaterialStats newCore = new CoreMaterialStats(durability, coreStats.defense);
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
