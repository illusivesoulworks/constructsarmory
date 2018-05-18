/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.lib.armor;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.Tags;

public class ArmorNBT {

    public int durability = 0;
    public float defense = 0;
    public float toughness = 0;
    public int modifiers = ArmorCore.DEFAULT_MODIFIERS;

    private final NBTTagCompound parent;

    public ArmorNBT() {
        parent = new NBTTagCompound();
    }

    public ArmorNBT(NBTTagCompound tag) {
        read(tag);
        parent = tag;
    }

    public ArmorNBT core(int slotIn, CoreMaterialStats core) {

        if(core != null) {
            durability += Math.round(core.durability * ArmorHelper.durabilityMultipliers[slotIn]);
            defense += core.defense;
        }

        this.durability = Math.max(1, this.durability);

        return this;
    }

    public ArmorNBT trim(int slotIn, TrimMaterialStats trim) {

        if(trim != null) {
            this.durability += Math.round(trim.extraDurability * ArmorHelper.durabilityMultipliers[slotIn]);
        }

        this.durability = Math.max(1, this.durability);

        return this;
    }

    public void plating(int slotIn, PlatesMaterialStats plating) {
        //(Average Core Durability + Average Trim Durability) * Average Plating Modifier + Average Plating Durability

        int dur = 0;
        float mod = 0;
        if(plating != null) {
            dur += Math.round(plating.durability * ArmorHelper.durabilityMultipliers[slotIn]);
            mod += plating.modifier;
            this.toughness += plating.toughness;
        }

        this.durability = Math.round((float) this.durability * mod);

        //Add plating durability
        this.durability += dur;

        this.durability = Math.max(1, this.durability);

    }

    public void read(NBTTagCompound tag) {
        durability = tag.getInteger(Tags.DURABILITY);
        defense = tag.getFloat(ArmorTagUtil.DEFENSE);
        toughness = tag.getFloat(ArmorTagUtil.TOUGHNESS);
        modifiers = tag.getInteger(Tags.FREE_MODIFIERS);
    }

    public void write(NBTTagCompound tag) {
        tag.setInteger(Tags.DURABILITY, durability);
        tag.setFloat(ArmorTagUtil.DEFENSE, defense);
        tag.setFloat(ArmorTagUtil.TOUGHNESS, toughness);
        tag.setInteger(Tags.FREE_MODIFIERS, modifiers);
    }

    public NBTTagCompound get() {
        NBTTagCompound tag = parent.copy();
        write(tag);

        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        ArmorNBT armorNBT = (ArmorNBT) o;

        if(durability != armorNBT.durability) {
            return false;
        }
        if(Float.compare(armorNBT.defense, defense) != 0) {
            return false;
        }
        if(Float.compare(armorNBT.toughness, toughness) != 0) {
            return false;
        }
        return modifiers == armorNBT.modifiers;

    }

    @Override
    public int hashCode() {
        int result = durability;
        result = 31 * result + (defense != +0.0f ? Float.floatToIntBits(defense) : 0);
        result = 31 * result + (toughness != +0.0f ? Float.floatToIntBits(toughness) : 0);
        result = 31 * result + modifiers;
        return result;
    }
}
