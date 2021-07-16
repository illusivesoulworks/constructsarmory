package top.theillusivec4.constructsarmory.common.stats.impl;

import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.common.stats.base.AbstractCoreMaterialStats;

public class LeggingsCoreMaterialStats extends AbstractCoreMaterialStats {

  public static final LeggingsCoreMaterialStats DEFAULT =
      new LeggingsCoreMaterialStats(1.0f, 1.0f, 1.0f);

  public LeggingsCoreMaterialStats(float durability, float armor, float toughness) {
    super(ArmorMaterialStatsIdentifiers.LEGGINGS_CORE, durability, 15, armor, toughness);
  }
}
