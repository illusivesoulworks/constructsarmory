/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.lib.armor;

public class ArmorModifications {

    public float armor;
    public float toughness;
    public float armorMod = 1;
    public float toughnessMod = 1;
    public float effective = 1;

    public ArmorModifications() {
        armor = 0;
        toughness = 0;
    }

    public ArmorModifications(float armor, float toughness) {
        this.armor = armor;
        this.toughness = toughness;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public void addArmor(float armor) {
        this.armor += armor;
    }

    public void setToughness(float toughness) {
        this.toughness = toughness;
    }

    public void addToughness(float toughness) {
        this.toughness += toughness;
    }

    public void addArmorMod(float armorMod) {
        this.armorMod += armorMod;
    }

    public void addToughnessMod(float toughnessMod) {
        this.toughnessMod += toughnessMod;
    }

    public void addEffectiveness(float effective) {
        this.effective += effective;
    }
}
