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
