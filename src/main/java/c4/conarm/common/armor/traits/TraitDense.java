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

package c4.conarm.common.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitDense extends AbstractArmorTrait {

    public TraitDense() {
        super("dense", 0xffffff);
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        float durability = ToolHelper.getCurrentDurability(armor);
        float maxDurability = ToolHelper.getMaxDurability(armor);

        float chance = 0.75F * (1F - durability / maxDurability);
        chance = (float) Math.pow(chance, 3);

        if(chance > random.nextFloat()) {
            newDamage -= Math.max(damage / 2, 1);
        }

        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }
}
