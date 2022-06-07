package top.theillusivec4.constructsarmory.common.stat;

import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class ConstructsArmoryStats {

  public static final FloatToolStat MOVEMENT_SPEED =
      ToolStats.register(new FloatToolStat(name("movement_speed"), 0xff78a0cd, 0.01f, 0f, 2048f));

  public static void init() {
    // NO-OP
  }

  private static ToolStatId name(String name) {
    return new ToolStatId(ConstructsArmoryMod.MOD_ID, name);
  }
}
