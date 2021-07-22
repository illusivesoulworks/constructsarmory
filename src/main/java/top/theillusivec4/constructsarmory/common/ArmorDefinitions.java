package top.theillusivec4.constructsarmory.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.inventory.EquipmentSlotType;
import slimeknights.tconstruct.library.tools.ToolBaseStatDefinition;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import top.theillusivec4.constructsarmory.common.stat.ArmorStatsBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArmorDefinitions {

  private static final ToolBaseStatDefinition BASE = new ToolBaseStatDefinition.Builder().build();

  public static final ToolDefinition HELMET = ToolDefinition
      .builder(BASE)
      .setStatsBuilder((toolDefinition, mats) -> ArmorStatsBuilder
          .from(toolDefinition, mats, EquipmentSlotType.HEAD))
      .addPart(ArmorParts.helmetCore)
      .addPart(ArmorParts.armorPlate)
      .addPart(ArmorParts.armorTrim)
      .build();
  public static final ToolDefinition CHESTPLATE = ToolDefinition
      .builder(BASE)
      .setStatsBuilder((toolDefinition, mats) -> ArmorStatsBuilder
          .from(toolDefinition, mats, EquipmentSlotType.CHEST))
      .addPart(ArmorParts.chestplateCore)
      .addPart(ArmorParts.armorPlate)
      .addPart(ArmorParts.armorTrim)
      .build();
  public static final ToolDefinition LEGGINGS = ToolDefinition
      .builder(BASE)
      .setStatsBuilder((toolDefinition, mats) -> ArmorStatsBuilder
          .from(toolDefinition, mats, EquipmentSlotType.LEGS))
      .addPart(ArmorParts.leggingsCore)
      .addPart(ArmorParts.armorPlate)
      .addPart(ArmorParts.armorTrim)
      .build();
  public static final ToolDefinition BOOTS = ToolDefinition
      .builder(BASE)
      .setStatsBuilder((toolDefinition, mats) -> ArmorStatsBuilder
          .from(toolDefinition, mats, EquipmentSlotType.FEET))
      .addPart(ArmorParts.bootsCore)
      .addPart(ArmorParts.armorPlate)
      .addPart(ArmorParts.armorTrim)
      .build();
}
