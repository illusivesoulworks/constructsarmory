package top.theillusivec4.constructsarmory.common.stat;

import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

public class ConstructsArmoryMaterialStats {

  public static void setup() {
    IMaterialRegistry registry = MaterialRegistry.getInstance();
    registry.registerStatType(PlateMaterialStats.DEFAULT, PlateMaterialStats.class);
    registry.registerStatType(MailMaterialStats.DEFAULT, MailMaterialStats.class);
    registry.registerStatType(TrimMaterialStats.DEFAULT, TrimMaterialStats.class);
  }
}
