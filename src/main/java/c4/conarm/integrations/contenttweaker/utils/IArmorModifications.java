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

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.conarm.utils.IArmorModifications")
@ZenRegister
public interface IArmorModifications {

  @ZenGetter("armor")
  float getArmor();

  @ZenSetter("armor")
  void setArmor(float armor);

  @ZenMethod
  void addArmor(float armor);

  @ZenGetter("toughness")
  float getToughness();

  @ZenSetter("toughness")
  void setToughness(float toughness);

  @ZenMethod
  void addToughness(float toughness);

  @ZenGetter("armorMod")
  float getArmorMod();

  @ZenSetter("armorMod")
  void setArmorMod(float armorMod);

  @ZenMethod
  void addArmorMod(float armorMod);

  @ZenGetter("toughnessMod")
  float getToughnessMod();

  @ZenSetter("toughnessMod")
  void setToughnessMod(float toughnessMod);

  @ZenMethod
  void addToughnessMod(float toughnessMod);

  @ZenGetter("effectiveness")
  float getEffectiveness();

  @ZenSetter("effectiveness")
  void setEffectiveness(float effective);

  @ZenMethod
  void addEffectiveness(float effective);
}
