/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

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
