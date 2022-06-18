package top.theillusivec4.constructsarmory.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.client.MaterialArmorModel;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryStats;

public class MaterialArmorItem extends ModifiableArmorItem {

  private static final UUID[] ARMOR_MODIFIERS =
      new UUID[] {UUID.fromString("845db27c-c624-495f-8c9f-6020a9a58b6b"),
          UUID.fromString("d8499b04-0e66-4726-ab29-64469d734e0d"),
          UUID.fromString("9f3d476d-c118-4544-8365-64846904b48e"),
          UUID.fromString("2ad3f246-fee1-4e67-b886-69fd380bb150")};

  public MaterialArmorItem(ModifiableArmorMaterial material, ArmorSlotType slotType,
                           Properties properties) {
    super(material, slotType, properties);
  }

  @Nullable
  @Override
  @OnlyIn(Dist.CLIENT)
  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack stack,
                                                   EquipmentSlotType armorSlot, A base) {
    return MaterialArmorModel.getModel(stack, armorSlot, base);
  }

  @Nonnull
  @Override
  public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
      @Nonnull IModifierToolStack tool, @Nonnull EquipmentSlotType slot) {

    if (slot != getEquipmentSlot()) {
      return ImmutableMultimap.of();
    }
    Multimap<Attribute, AttributeModifier> origin = super.getAttributeModifiers(tool, slot);
    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

    if (!tool.isBroken()) {
      StatsNBT statsNBT = tool.getStats();
      UUID uuid = ARMOR_MODIFIERS[slot.getIndex()];
      builder.putAll(origin);
      builder.put(Attributes.MOVEMENT_SPEED,
          new AttributeModifier(uuid, "constructsarmory.armor.movement_speed",
              statsNBT.getFloat(ConstructsArmoryStats.MOVEMENT_SPEED),
              AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
    return builder.build();
  }
}
