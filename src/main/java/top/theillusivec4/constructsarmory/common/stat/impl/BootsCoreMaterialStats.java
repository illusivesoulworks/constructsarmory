package top.theillusivec4.constructsarmory.common.stat.impl;

import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.common.stat.base.AbstractCoreMaterialStats;

public class BootsCoreMaterialStats extends AbstractCoreMaterialStats {

  public static final BootsCoreMaterialStats DEFAULT = new BootsCoreMaterialStats(1.0f, 1.0f, 1.0f);

  public BootsCoreMaterialStats(float durability, float armor, float toughness) {
    super(ArmorMaterialStatsIdentifiers.BOOTS_CORE, durability, 13, armor, toughness);
  }
}
