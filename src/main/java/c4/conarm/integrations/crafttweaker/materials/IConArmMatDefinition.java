/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.integrations.crafttweaker.materials;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.conarm.IConArmMatDefinition")
@ZenRegister
public interface IConArmMatDefinition {

    @ZenGetter("stack")
    IConArmMaterial asMaterial();

    @ZenGetter("name")
    String getName();

    @ZenGetter("displayName")
    String getDisplayName();
}
