/*
 * Copyright (c) 2018-2019 <C4>
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

package c4.conarm.integrations.jei;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.Item;
import slimeknights.tconstruct.plugin.jei.interpreter.TableSubtypeInterpreter;
import slimeknights.tconstruct.plugin.jei.interpreter.ToolSubtypeInterpreter;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistry registry) {

        ToolSubtypeInterpreter armorInterpreter = new ToolSubtypeInterpreter();
        TableSubtypeInterpreter tableInterpreter = new TableSubtypeInterpreter();

        for (ArmorCore armor : ArmoryRegistry.getArmor()) {
            registry.registerSubtypeInterpreter(armor, armorInterpreter);
        }

        registry.registerSubtypeInterpreter(Item.getItemFromBlock(ConstructsRegistry.armorForge), tableInterpreter);
    }
}
