package top.theillusivec4.constructsarmory.common;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.ToolItem;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.constructsarmory.api.ArmorStats;

public class ModifiableArmorItem extends ToolItem {

  private static final UUID[] ARMOR_MODIFIERS =
      new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
          UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
          UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
          UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

  protected final EquipmentSlotType slotType;
  protected final UUID uuid;

  protected ModifiableArmorItem(EquipmentSlotType slotType, Properties properties,
                                ToolDefinition toolDefinition) {
    super(properties, toolDefinition);
    this.slotType = slotType;
    this.uuid = ARMOR_MODIFIERS[slotType.getIndex()];
  }

  @Nullable
  @Override
  public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
    return this.slotType;
  }

  @Nonnull
  @Override
  public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
      @Nonnull EquipmentSlotType slot, @Nonnull ItemStack stack) {
    CompoundNBT nbt = stack.getTag();

    if (nbt == null || nbt.getBoolean(ToolBuildHandler.KEY_DISPLAY_TOOL)) {
      return ImmutableMultimap.of();
    }
    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
    ToolStack tool = ToolStack.from(stack);

    if (!tool.isBroken() && slot == this.slotType) {
      StatsNBT statsNBT = tool.getStats();
      builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "constructsarmory.armor.armor",
          statsNBT.getFloat(ArmorStats.ARMOR), AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.ARMOR_TOUGHNESS,
          new AttributeModifier(uuid, "constructsarmory.armor.toughness",
              statsNBT.getFloat(ArmorStats.TOUGHNESS), AttributeModifier.Operation.ADDITION));
    }
    return builder.build();
  }
}
