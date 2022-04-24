package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryDefinitions;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;

public class ArmorDefinitionDataProvider extends AbstractToolDefinitionDataProvider {

  public ArmorDefinitionDataProvider(DataGenerator generator) {
    super(generator, ConstructsArmoryMod.MOD_ID);
  }

  @Override
  protected void addToolDefinitions() {
    defineArmor(ConstructsArmoryDefinitions.MATERIAL_ARMOR)
        .part(ArmorSlotType.HELMET, ConstructsArmoryItems.HEAD_PLATE.get(), 1)
        .part(ArmorSlotType.HELMET, ConstructsArmoryItems.MAIL.get(), 1)
        .part(ArmorSlotType.CHESTPLATE, ConstructsArmoryItems.BODY_PLATE.get(), 1)
        .part(ArmorSlotType.CHESTPLATE, ConstructsArmoryItems.MAIL.get(), 1)
        .part(ArmorSlotType.LEGGINGS, ConstructsArmoryItems.LEGS_PLATE.get(), 1)
        .part(ArmorSlotType.LEGGINGS, ConstructsArmoryItems.MAIL.get(), 1)
        .part(ArmorSlotType.BOOTS, ConstructsArmoryItems.FEET_PLATE.get(), 1)
        .part(ArmorSlotType.BOOTS, ConstructsArmoryItems.MAIL.get(), 1)
        .stat(ToolStats.ARMOR, 0)
        .stat(ToolStats.ARMOR_TOUGHNESS, 0)
        .stat(ToolStats.KNOCKBACK_RESISTANCE, 0)
        .startingSlots(SlotType.UPGRADE, 1)
        .startingSlots(SlotType.DEFENSE, 2)
        .startingSlots(SlotType.ABILITY, 1);
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Armor Definition Data Generator";
  }
}
