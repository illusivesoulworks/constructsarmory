/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;

public class ModSilkstep extends ArmorModifierTrait {

    public ModSilkstep() {
        super("silkstep", 0xfbe28b);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (player.fallDistance > 0.0F) {
            player.fallDistance = 0.0F;
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET;
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);

//        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
//        data.armor = Math.max(0f, data.armor - 2f);
//        data.toughness = Math.max(0f, data.toughness - 1f);
//
//        TagUtil.setToolTag(rootCompound, data.get());
    }
}
