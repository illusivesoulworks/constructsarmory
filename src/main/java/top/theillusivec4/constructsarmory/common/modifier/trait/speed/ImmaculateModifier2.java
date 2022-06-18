package top.theillusivec4.constructsarmory.common.modifier.trait.speed;

import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ImmaculateModifier2 extends ImmaculateModifier {

  public ImmaculateModifier2() {
    super(0xd58f36);
  }

  @Override
  protected float getTotalBoost(IModifierToolStack armor, int level) {
    int durability = armor.getCurrentDurability();
    int fullMax = armor.getStats().getInt(ToolStats.DURABILITY);
    return boost(durability, 0.025f, fullMax / 4, fullMax) * level;
  }
}
