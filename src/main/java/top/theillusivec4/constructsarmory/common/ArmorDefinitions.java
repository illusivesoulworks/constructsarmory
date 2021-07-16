package top.theillusivec4.constructsarmory.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.library.tools.ToolBaseStatDefinition;
import slimeknights.tconstruct.library.tools.ToolDefinition;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArmorDefinitions {

  private static final ToolBaseStatDefinition BASE = new ToolBaseStatDefinition.Builder().build();

  public static final ToolDefinition HELMET = ToolDefinition
      .builder(BASE)
      .addPart(ArmorParts.helmetCore).addPart(ArmorParts.armorPlate).addPart(ArmorParts.armorTrim)
      .build();
  public static final ToolDefinition CHESTPLATE = ToolDefinition
      .builder(BASE)
      .addPart(ArmorParts.chestplateCore).addPart(ArmorParts.armorPlate)
      .addPart(ArmorParts.armorTrim)
      .build();
  public static final ToolDefinition LEGGINGS = ToolDefinition
      .builder(BASE)
      .addPart(ArmorParts.leggingsCore).addPart(ArmorParts.armorPlate).addPart(ArmorParts.armorTrim)
      .build();
  public static final ToolDefinition BOOTS = ToolDefinition
      .builder(BASE)
      .addPart(ArmorParts.bootsCore).addPart(ArmorParts.armorPlate).addPart(ArmorParts.armorTrim)
      .build();
}
