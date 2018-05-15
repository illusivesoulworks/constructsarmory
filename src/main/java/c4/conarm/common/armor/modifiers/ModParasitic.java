/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class ModParasitic extends ArmorModifierTrait {

    public ModParasitic() {
        super("parasitic", 0x5e0000);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (world.isRemote) {
            return;
        }

        if (needsRepair(armor)) {
            if (hasMoreHealthThanDurability(armor, player) && random.nextFloat() < 0.1F / 20) {
                //Make sure we don't actually kill the player we're feeding off
                if (player.getHealth() > 2.0F) {
                    ArmorHelper.healArmor(armor, getDurabilityPerHP(), player, EntityLiving.getSlotForItemStack(armor).getIndex());
                    player.setHealth(player.getHealth() - 1.0F);
                }
            }
        }
    }

    private int getDurabilityPerHP() {
        return 5;
    }

    //We only siphon health if the health percentage is higher than our durability percentage
    private boolean hasMoreHealthThanDurability(ItemStack stack, EntityPlayer player) {
        float durabilityPerc = ((float) ToolHelper.getCurrentDurability(stack)) / ((float) ToolHelper.getMaxDurability(stack));
        float healthPerc = player.getHealth() / player.getMaxHealth();
        return durabilityPerc < healthPerc;
    }

    private boolean needsRepair(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItemDamage() > 0 && !ToolHelper.isBroken(itemStack);
    }
}
