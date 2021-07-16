package top.theillusivec4.constructsarmory.api;

import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class ArmorStats {

  public static final FloatToolStat ARMOR =
      ToolStats.register(new FloatToolStat(getId("armor"), 0xFFD76464, 1, 0, 2048f));
  public static final FloatToolStat TOUGHNESS =
      ToolStats.register(new FloatToolStat(getId("toughness"), 0xFF8547CC, 1, 0, 1024f));

  private static ToolStatId getId(String name) {
    return new ToolStatId(ConstructsArmoryMod.MOD_ID, name);
  }
}
