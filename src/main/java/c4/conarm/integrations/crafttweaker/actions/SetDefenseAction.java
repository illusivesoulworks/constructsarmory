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
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import crafttweaker.IAction;
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
