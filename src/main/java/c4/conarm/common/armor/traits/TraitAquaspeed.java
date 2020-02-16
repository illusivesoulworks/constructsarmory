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

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TraitAquaspeed extends AbstractArmorTrait {

    protected static final UUID[] SWIMSPEED_MODIFIERS = new UUID[]{ UUID.fromString("21929d25-2631-4df7-9bf4-a80f084e7bf4"),
                                                                    UUID.fromString("2dfcd5ff-f0c3-4012-a438-e961fcd7420f"),
                                                                    UUID.fromString("eb94ffe8-959b-4440-a15b-00122626eed6"),
                                                                    UUID.fromString("e673b8ee-7241-4fbd-9aa3-2bf3b8c7e807") };
    private static final double SWIMSPEED_PER_LEVEL = 0.675D;

    public TraitAquaspeed() {
        super("aquaspeed", TextFormatting.AQUA);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(SWIMSPEED_MODIFIERS[slot.getIndex()], "Aquaspeed trait modifier", SWIMSPEED_PER_LEVEL, 0));
        }
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {

        if (player.isInWater() && player.isSneaking()) {
            player.motionY *= 1.1D;
        }
    }
}