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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TraitAquaspeed extends AbstractArmorTrait {

    protected static final UUID[] SWIMSPEED_MODIFIERS = new UUID[]{ UUID.fromString("21929d25-2631-4df7-9bf4-a80f084e7bf4"),
                                                                    UUID.fromString("2dfcd5ff-f0c3-4012-a438-e961fcd7420f"),
                                                                    UUID.fromString("eb94ffe8-959b-4440-a15b-00122626eed6"),
                                                                    UUID.fromString("e673b8ee-7241-4fbd-9aa3-2bf3b8c7e807") };
    private static final double SWIMSPEED_PER_LEVEL = 0.30D;

    public TraitAquaspeed() {
        super("aquaspeed", TextFormatting.AQUA);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(SWIMSPEED_MODIFIERS[slot.getIndex()], "Aquaspeed trait modifier", SWIMSPEED_PER_LEVEL, 0));
        }
    }
}