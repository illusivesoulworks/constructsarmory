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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class TraitLightweight extends AbstractArmorTrait {

    protected static final UUID[] SPEED_MODIFIERS = new UUID[]{ UUID.fromString("6b1a5e14-1589-4273-86c4-cc0df937c108"),
                                                                UUID.fromString("69c21f84-449d-4e18-89c1-b9f7b5ca98f0"),
                                                                UUID.fromString("37f79f7c-5cca-426b-824b-9ba2ced92337"),
                                                                UUID.fromString("1316663f-febe-430f-8714-94bf21118ab6") };
    private static final double SPEED_PER_LEVEL = 0.05D;

    public TraitLightweight() {
        super("lightweight", 0x00ff00);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(SPEED_MODIFIERS[slot.getIndex()], "Lightweight trait modifier", SPEED_PER_LEVEL, 2));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(SPEED_PER_LEVEL)));
    }
}
