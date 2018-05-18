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

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitPetravidity extends AbstractArmorTrait {

    public TraitPetravidity() {
        super("petravidity", TextFormatting.RED);
    }

    @Override
    public void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt) {
        ItemStack stack = item.getItem();
        int toRepair = ToolHelper.getMaxDurability(armor) - ToolHelper.getCurrentDurability(armor);
        if (toRepair > 0 && stack.getItem() == Item.getItemFromBlock(Blocks.STONE)) {
            int count = stack.getCount();
            if (toRepair >= count) {
                ArmorHelper.healArmor(armor, count, evt.getEntityPlayer(), EntityLiving.getSlotForItemStack(armor).getIndex());
                item.setDead();
            } else {
                ArmorHelper.healArmor(armor, toRepair, evt.getEntityPlayer(), EntityLiving.getSlotForItemStack(armor).getIndex());
                item.getItem().shrink(toRepair);
            }
            evt.setCanceled(true);
        }
    }
}
