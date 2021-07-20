package top.theillusivec4.constructsarmory.common.stat.impl;

import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.common.stat.base.AbstractCoreMaterialStats;

public class HelmetCoreMaterialStats extends AbstractCoreMaterialStats {

  public static final HelmetCoreMaterialStats DEFAULT =
      new HelmetCoreMaterialStats(1.0f, 1.0f, 1.0f);

  public HelmetCoreMaterialStats(float durability, float armor, float toughness) {
    super(ArmorMaterialStatsIdentifiers.HELMET_CORE, durability, 11, armor, toughness);
  }
}
