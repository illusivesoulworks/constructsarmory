/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
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
