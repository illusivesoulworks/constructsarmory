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

package c4.conarm.integrations.contenttweaker.traits;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
@ZenClass("mods.contenttweaker.conarm.ArmorTraitDataRepresentation")
@ZenRegister
@ModOnly("contenttweaker")
public class ConArmTraitDataRepresentation {

    private final ModifierNBT.IntegerNBT data;

    public ConArmTraitDataRepresentation(ModifierNBT.IntegerNBT data) {
        this.data = data;
    }

    @ZenGetter("color")
    public int getColor() {
        return data.color;
    }

    @ZenSetter("color")
    public void setColor(int color) {
        data.color = color;
    }

    @ZenGetter("current")
    public int getCurrent() {
        return data.current;
    }

    @ZenSetter("current")
    public void setCurrent(int current) {
        data.current = current;
    }

    @ZenGetter("extraInfo")
    public String getExtraInfo() {
        return data.extraInfo;
    }

    @ZenSetter("extraInfo")
    public void setExtraInfo(String extraInfo) {
        data.extraInfo = extraInfo;
    }

    @ZenGetter("identifier")
    public String getIdentifier() {
        return data.identifier;
    }

    @ZenSetter("identifier")
    public void setIdentifier(String identifier) {
        data.identifier = identifier;
    }

    @ZenGetter("level")
    public int getLevel() {
        return data.level;
    }

    @ZenSetter("level")
    public void setLevel(int level) {
        data.level = level;
    }

    @ZenGetter("max")
    public int getMax() {
        return data.max;
    }

    @ZenSetter("max")
    public void setMax(int max) {
        data.max = max;
    }

    @ZenGetter("info")
    @ZenMethod
    public String calcInfo() {
        return data.calcInfo();
    }

    @ZenGetter("colorString")
    @ZenMethod
    public String getColorString() {
        return data.getColorString();
    }
}
