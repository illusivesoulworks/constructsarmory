package top.theillusivec4.constructsarmory.api;

import java.util.HashMap;
import java.util.Map;
import slimeknights.tconstruct.tools.item.ArmorSlotType;

public class ArmorStatsCalculator {

  private static final int[] ARMOR_STEPS = new int[] {3, 2, 1, 0, 3, 2, 3};
  private static final Map<Float, float[]> ARMOR_VALUES = new HashMap<>();
  private static final Map<Integer, int[]> DURABILITY_VALUES = new HashMap<>();

  public static float[] getArmorStats(float armor) {
    final float finalArmor = armor;
    return ARMOR_VALUES.computeIfAbsent(finalArmor, (k) -> {
      float points = finalArmor;
      float[] result = new float[] {0.0f, 0.0f, 0.0f, 0.0f};
      float step;

      if (k >= 7.0f) {
        step = 1.0f;
      } else if (k >= 4.0f) {
        step = 0.5f;
      } else {
        step = 0.1f;
      }

      while (points >= step) {

        for (int i = 0; i < ARMOR_STEPS.length && points >= step; i++) {
          int index = ARMOR_STEPS[i];
          result[index] += step;
          points -= step;
        }
      }
      result[0] += points;
      return result;
    });
  }

  public static float getArmorStat(float armor, ArmorSlotType slotType) {
    int index = 0;

    if (slotType == ArmorSlotType.HELMET) {
      index = 1;
    } else if (slotType == ArmorSlotType.LEGGINGS) {
      index = 2;
    } else if (slotType == ArmorSlotType.CHESTPLATE) {
      index = 3;
    }
    return getArmorStats(armor)[index];
  }

  public static int[] getDurabilityStats(int durability) {
    return DURABILITY_VALUES.computeIfAbsent(durability,
        (k) -> new int[] {(int) Math.max(1, durability * 0.8125f),
            (int) Math.max(1, durability * 0.6875f), (int) Math.max(1, durability * 0.9375f),
            Math.max(1, durability)});
  }

  public static int getDurabilityStat(int durability, ArmorSlotType slotType) {
    int index = 0;

    if (slotType == ArmorSlotType.HELMET) {
      index = 1;
    } else if (slotType == ArmorSlotType.LEGGINGS) {
      index = 2;
    } else if (slotType == ArmorSlotType.CHESTPLATE) {
      index = 3;
    }
    return getDurabilityStats(durability)[index];
  }
}
