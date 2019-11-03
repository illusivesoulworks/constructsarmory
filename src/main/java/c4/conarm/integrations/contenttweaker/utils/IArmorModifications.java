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
