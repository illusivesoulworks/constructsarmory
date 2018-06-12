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

package c4.conarm.integrations.crafttweaker.materials;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.conarm.IConArmMaterial")
@ZenRegister
public interface IConArmMaterial {

    @ZenGetter
    String getName();

    Object getInternal();

    @ZenGetter("definition")
    IConArmMatDefinition getDefinition();

    @ZenMethod
    boolean matches(IConArmMaterial var1);

    @ZenSetter("durabilityCore")
    void setDurabilityCore(float durability);

    @ZenGetter("durabilityCore")
    float getDurabilityCore();

    @ZenSetter("durabilityPlates")
    void setDurabilityPlates(float durability);

    @ZenGetter("durabilityPlates")
    float getDurabilityPlates();

    @ZenSetter("durabilityTrim")
    void setDurabilityTrim(float durability);

    @ZenGetter("durabilityTrim")
    float getDurabilityTrim();

    @ZenSetter("defense")
    void setDefense(float defense);

    @ZenGetter("defense")
    float getDefense();

    @ZenSetter("modifier")
    void setModifier(float modifier);

    @ZenGetter("modifier")
    float getModifier();

    @ZenSetter("toughness")
    void setToughness(float modifier);

    @ZenGetter("toughness")
    float getToughness();

}
