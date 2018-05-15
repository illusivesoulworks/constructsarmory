/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.integrations.crafttweaker.actions;

import c4.conarm.integrations.crafttweaker.materials.IConArmMaterial;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.PlatesMaterialStats;
import crafttweaker.IAction;
import slimeknights.tconstruct.library.materials.Material;

public class SetToughnessAction implements IAction {

    private final IConArmMaterial material;
    private final float newValue;

    public SetToughnessAction(IConArmMaterial material, float newValue) {
        this.material = material;
        this.newValue = newValue;
    }

    private static void set(Material material, float toughness) {
        PlatesMaterialStats oldStats = material.getStats(ArmorMaterialType.PLATES);
        PlatesMaterialStats newPlates = new PlatesMaterialStats(oldStats.modifier, oldStats.durability, toughness);
        material.addStats(newPlates);
    }

    @Override
    public void apply() {
        set((Material) material.getInternal(), newValue);
    }


    @Override
    public String describe() {
        return "Setting toughness of " + material.getName() + " to " + newValue;
    }

}
