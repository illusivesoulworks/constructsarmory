/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class ModProjectileResistant extends AbstractModResistant {

    public ModProjectileResistant() {
        super("projectile_resistant", 0x10574b, Enchantments.PROJECTILE_PROTECTION);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {

        return enchantment != Enchantments.BLAST_PROTECTION && enchantment != Enchantments.FIRE_PROTECTION && enchantment != Enchantments.PROTECTION;
    }
}
