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

package c4.conarm.integrations.contenttweaker.utils;

import c4.conarm.lib.armor.ArmorModifications;

public class CTArmorModifications implements IArmorModifications {

  private final ArmorModifications mods;

  public CTArmorModifications(ArmorModifications mods) {
    this.mods = mods;
  }

  @Override
  public float getArmor() {
    return mods.armor;
  }

  @Override
  public void setArmor(float armor) {
    mods.setArmor(armor);
  }

  @Override
  public void addArmor(float armor) {
    mods.addArmor(armor);
  }

  @Override
  public float getToughness() {
    return mods.toughness;
  }

  @Override
  public void setToughness(float toughness) {
    mods.setToughness(toughness);
  }

  @Override
  public void addToughness(float toughness) {
    mods.addToughness(toughness);
  }

  @Override
  public float getArmorMod() {
    return mods.armorMod;
  }

  @Override
  public void setArmorMod(float armorMod) {
    mods.armorMod = armorMod;
  }

  @Override
  public void addArmorMod(float armorMod) {
    mods.addArmorMod(armorMod);
  }

  @Override
  public float getToughnessMod() {
    return mods.toughnessMod;
  }

  @Override
  public void setToughnessMod(float toughnessMod) {
    mods.toughnessMod = toughnessMod;
  }

  @Override
  public void addToughnessMod(float toughnessMod) {
    mods.addToughnessMod(toughnessMod);
  }

  @Override
  public float getEffectiveness() {
    return mods.effective;
  }

  @Override
  public void setEffectiveness(float effective) {
    mods.effective = effective;
  }

  @Override
  public void addEffectiveness(float effective) {
    mods.addEffectiveness(effective);
  }
}
