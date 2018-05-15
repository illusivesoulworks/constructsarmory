/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.integrations.jei;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.ISubtypeRegistry;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistry registry) {

        ArmorSubtypeInterpreter armorInterpreter = new ArmorSubtypeInterpreter();
        for (ArmorCore armor : ArmoryRegistry.getArmor()) {
            registry.registerSubtypeInterpreter(armor, armorInterpreter);
        }
    }
}
