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
