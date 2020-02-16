/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import c4.conarm.lib.utils.ConstructUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ModSpeedy extends ArmorModifierTrait {

    protected static final UUID[] SPEED_MODIFIERS = new UUID[]{
            UUID.fromString("857af40b-def8-47e3-a838-527933eca586"),
            UUID.fromString("1fd2c8fb-5a76-4e6f-bccd-c27f2287ad1b"),
            UUID.fromString("54e47051-19ac-4e59-9e54-4f256484408a"),
            UUID.fromString("ac16532f-d0b0-4b23-a89a-85ecaa3b5d7d") };

    protected int max;

    public ModSpeedy(int count) {
        super("speedy", 0x910000, 3, count);
        this.max = count;
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if(slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(SPEED_MODIFIERS[slot.getIndex()], "Speedy modifier", getSpeedBonus(stack), 2));
        }
    }

    protected float getSpeedBonus(ItemStack stack) {
        NBTTagCompound modifierTag = new NBTTagCompound();
        NBTTagList tagList = TagUtil.getModifiersTagList(TagUtil.getTagSafe(stack));
        int index = TinkerUtil.getIndexInList(tagList, identifier);
        if(index >= 0) {
            modifierTag = tagList.getCompoundTagAt(index);
        }
        ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);
        return getSpeedBonus(modData);
    }

    protected float getSpeedBonus(ModifierNBT.IntegerNBT modData) {
        return 0.05F * modData.current / this.max;
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return getLeveledTooltip(modifierTag, detailed);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getIdentifier());

        ImmutableList.Builder<String> builder = ImmutableList.builder();

        float bonus = getSpeedBonus(ModifierNBT.readInteger(modifierTag));
        builder.add(Util.translateFormatted(loc, ConstructUtils.dfPercentSpec.format(bonus)));
        return builder.build();
    }
}
