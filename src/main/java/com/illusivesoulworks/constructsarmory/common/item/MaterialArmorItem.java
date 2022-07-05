/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.constructsarmory.common.item;

import static com.illusivesoulworks.constructsarmory.common.stat.impl.PlateMaterialStats.PERCENT_FORMAT;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.client.MaterialArmorModel;
import com.illusivesoulworks.constructsarmory.common.stat.ConstructsArmoryStats;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.TooltipFlag;
import slimeknights.tconstruct.library.utils.TooltipKey;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.item.ArmorSlotType;

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

  public static final BiPredicate<Attribute, AttributeModifier.Operation>
      SHOW_ARMOR_ATTRIBUTES = (att, op) -> (op != AttributeModifier.Operation.ADDITION &&
      !(op == AttributeModifier.Operation.MULTIPLY_TOTAL && att == Attributes.MOVEMENT_SPEED)) ||
      (att != Attributes.ARMOR && att != Attributes.ARMOR_TOUGHNESS &&
          att != Attributes.KNOCKBACK_RESISTANCE && att != Attributes.MOVEMENT_SPEED);

  @Nonnull
  @Override
  public List<ITextComponent> getStatInformation(@Nonnull IModifierToolStack tool,
                                                 @Nullable PlayerEntity player,
                                                 @Nonnull List<ITextComponent> tooltips,
                                                 @Nonnull TooltipKey key,
                                                 @Nonnull TooltipFlag tooltipFlag) {
    tooltips = getArmorStats(tool, player, tooltips, key, tooltipFlag);
    TooltipUtil.addAttributes(this, tool, player, tooltips, SHOW_ARMOR_ATTRIBUTES,
        getEquipmentSlot());
    return tooltips;
  }

  public static List<ITextComponent> getArmorStats(IModifierToolStack tool,
                                                   @Nullable PlayerEntity player,
                                                   List<ITextComponent> tooltip, TooltipKey key,
                                                   TooltipFlag flag) {
    TooltipBuilder builder = new TooltipBuilder(tool, tooltip);
    Item item = tool.getItem();

    if (TinkerTags.Items.DURABILITY.contains(item)) {
      builder.addDurability();
    }

    if (TinkerTags.Items.ARMOR.contains(item)) {
      builder.add(ToolStats.ARMOR);
      builder.add(ToolStats.ARMOR_TOUGHNESS);
      builder.add(ToolStats.KNOCKBACK_RESISTANCE.formatValue(
          tool.getStats().getFloat(ToolStats.KNOCKBACK_RESISTANCE) * 10f));
      builder.add(new TranslationTextComponent(
          "tool_stat." + ConstructsArmoryMod.MOD_ID + ".movement_speed").appendSibling(
          new StringTextComponent(PERCENT_FORMAT.format(
              tool.getStats().getFloat(ConstructsArmoryStats.MOVEMENT_SPEED))).modifyStyle(
              style -> style.setColor(ConstructsArmoryStats.MOVEMENT_SPEED.getColor()))));
    }

    if (TinkerTags.Items.CHESTPLATES.contains(item) &&
        tool.getModifierLevel(TinkerModifiers.unarmed.get()) > 0) {
      builder.addWithAttribute(ToolStats.ATTACK_DAMAGE, Attributes.ATTACK_DAMAGE);
    }
    builder.addAllFreeSlots();

    for (ModifierEntry entry : tool.getModifierList()) {
      entry.getModifier().addInformation(tool, entry.getLevel(), player, tooltip, key, flag);
    }
    return builder.getTooltips();
  }
}
