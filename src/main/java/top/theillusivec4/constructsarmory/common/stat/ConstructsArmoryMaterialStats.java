package top.theillusivec4.constructsarmory.common.stat;

import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import top.theillusivec4.constructsarmory.common.stat.impl.BootsCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.ChestplateCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.HelmetCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.LeggingsCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

public class ConstructsArmoryMaterialStats {

  public static void setup() {
    IMaterialRegistry registry = MaterialRegistry.getInstance();
    registry.registerStatType(HelmetCoreMaterialStats.DEFAULT, HelmetCoreMaterialStats.class);
    registry.registerStatType(ChestplateCoreMaterialStats.DEFAULT, ChestplateCoreMaterialStats.class);
    registry.registerStatType(LeggingsCoreMaterialStats.DEFAULT, LeggingsCoreMaterialStats.class);
    registry.registerStatType(BootsCoreMaterialStats.DEFAULT, BootsCoreMaterialStats.class);
    registry.registerStatType(PlateMaterialStats.DEFAULT, PlateMaterialStats.class);
    registry.registerStatType(TrimMaterialStats.DEFAULT, TrimMaterialStats.class);
  }
}
