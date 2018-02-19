package c4.conarm.lib.armor;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.Tags;

/*This class is a re-implementation of the
ToolNBT class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class ArmorNBT {

    public int durability;
    public float armor;
    public float toughness;
    public int modifiers;

    private final NBTTagCompound parent;

    public ArmorNBT() {
        durability = 0;
        armor = 0;
        toughness = 0;
        modifiers = ArmorCore.DEFAULT_MODIFIERS;
        parent = new NBTTagCompound();
    }

    public ArmorNBT(NBTTagCompound tag) {
        read(tag);
        parent = tag;
    }

    public ArmorNBT core(int slotIn, CoreMaterialStats... cores) {
        durability = 0;
        armor = 0;

        for(CoreMaterialStats core : cores) {
            if(core != null) {
                durability += core.durability * ArmorHelper.durabilityMultipliers[slotIn];
                armor += core.armor[slotIn];
            }
        }

        durability = Math.max(1, durability / cores.length);
        armor /= (float) cores.length;
        toughness /= (float) cores.length;

        return this;
    }

    public ArmorNBT trim(int slotIn, TrimMaterialStats... trims) {
        int dur = 0;
        for(TrimMaterialStats trim : trims) {
            if(trim != null) {
                dur += trim.extraDurability * ArmorHelper.durabilityMultipliers[slotIn];
            }
        }
        this.durability += Math.round((float) dur / (float) trims.length);

        return this;
    }

    public ArmorNBT plating(int slotIn, PlatesMaterialStats... plates) {
        //(Average Core Durability + Average Trim Durability) * Average Plating Modifier + Average Plating Durability

        int dur = 0;
        float modifier = 0f;
        toughness = 0;
        for(PlatesMaterialStats plating : plates) {
            if(plating != null) {
                dur += plating.durability * ArmorHelper.durabilityMultipliers[slotIn];
                modifier += plating.modifier;
                toughness += plating.toughness;
            }
        }

        modifier /= (float) plates.length;
        this.durability = Math.round((float) this.durability * modifier);

        //Add plating durability
        this.durability += Math.round((float) dur / (float) plates.length);

        this.durability = Math.max(1, this.durability);

        return this;
    }

    public void read(NBTTagCompound tag) {
        durability = tag.getInteger(Tags.DURABILITY);
        armor = tag.getFloat(ArmorTagUtil.ARMOR);
        toughness = tag.getFloat(ArmorTagUtil.TOUGHNESS);
        modifiers = tag.getInteger(Tags.FREE_MODIFIERS);
    }

    public void write(NBTTagCompound tag) {
        tag.setInteger(Tags.DURABILITY, durability);
        tag.setFloat(ArmorTagUtil.ARMOR, armor);
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
        if(Float.compare(armorNBT.armor, armor) != 0) {
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
        result = 31 * result + (armor != +0.0f ? Float.floatToIntBits(armor) : 0);
        result = 31 * result + (toughness != +0.0f ? Float.floatToIntBits(toughness) : 0);
        result = 31 * result + modifiers;
        return result;
    }
}
