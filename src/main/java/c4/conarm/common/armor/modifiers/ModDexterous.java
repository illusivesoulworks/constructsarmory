/*
 * Copyright (c) 2018-2019 <C4>
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

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.IToolMod;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ModDexterous extends ArmorModifierTrait {

    protected static final UUID MODIFIER = UUID.fromString("33659f5b-cf62-413e-b457-ded49c03fce7");
    private static final float BONUS = 0.15F;

    public ModDexterous() {
        super("dexterous", 0x660000);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if(slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(MODIFIER, "Dexterous modifier", BONUS, 2));
        }
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return otherModifier != ArmorModifiers.modTelekinetic && otherModifier != ArmorModifiers.modPowerful;
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST && super.canApplyCustom(stack);
    }
}
