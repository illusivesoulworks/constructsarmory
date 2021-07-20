package top.theillusivec4.constructsarmory.common.stat.impl;

import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.common.stat.base.AbstractCoreMaterialStats;

public class ChestplateCoreMaterialStats extends AbstractCoreMaterialStats {

  public static final ChestplateCoreMaterialStats DEFAULT =
      new ChestplateCoreMaterialStats(1.0f, 1.0f, 1.0f);

  public ChestplateCoreMaterialStats(float durability, float armor, float toughness) {
    super(ArmorMaterialStatsIdentifiers.CHESTPLATE_CORE, durability, 16, armor, toughness);
  }
}
