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

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TraitHeavy extends AbstractArmorTrait {

    protected static final UUID[] KNOCKBACK_MODIFIERS = new UUID[]{ UUID.fromString("a002af47-9672-444e-80d2-411e3693e0b6"),
                                                                    UUID.fromString("d154892c-3ad8-4e6a-90a3-e610d7e01b9a"),
                                                                    UUID.fromString("23c7bbf2-e8b0-4098-a73f-d018c52c7da6"),
                                                                    UUID.fromString("43f90b38-95ea-4d88-b2b4-1ea6eb5132a9") };
    private static final double KNOCKBACK_RESIST_PER_LEVEL = 0.25D;

    public TraitHeavy() {
        super("heavy", 0xffffff);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(KNOCKBACK_MODIFIERS[slot.getIndex()], "Heavy trait modifier", KNOCKBACK_RESIST_PER_LEVEL, 0));
        }
    }
}
