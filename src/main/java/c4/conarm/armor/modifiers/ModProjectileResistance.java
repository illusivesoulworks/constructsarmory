package c4.conarm.armor.modifiers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class ModProjectileResistance extends AbstractModResistance {

    public ModProjectileResistance() {
        super("projectile_resistance", 0x10574b, Enchantments.PROJECTILE_PROTECTION);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {

        return enchantment != Enchantments.BLAST_PROTECTION && enchantment != Enchantments.FIRE_PROTECTION && enchantment != Enchantments.PROTECTION;
    }
}
