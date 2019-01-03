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

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;

public class TraitAbsorbent extends AbstractArmorTrait {

    private static final float TOUGH_PER_LEVEL = 2.0F;
    private static final int[] ARMOR_VALUES = new int[] {2, 3, 4, 2};

    public TraitAbsorbent() {
        super("absorbent", TextFormatting.YELLOW);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (player.isInWater() || player.getEntityWorld().isRainingAt(player.getPosition())) {
            mods.addArmor(ARMOR_VALUES[slot]);
            mods.addToughness(TOUGH_PER_LEVEL);
        }
        return super.getModifications(player, mods, armor, source, damage, slot);
    }
}
