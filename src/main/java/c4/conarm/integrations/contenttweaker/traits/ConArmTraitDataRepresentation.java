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
