/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
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
