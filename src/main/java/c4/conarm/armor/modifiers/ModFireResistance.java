package c4.conarm.armor.modifiers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class ModFireResistance extends AbstractModResistance {

    public ModFireResistance() {
        super("fire_resistance", 0xea9e32, Enchantments.FIRE_PROTECTION);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {

        return enchantment != Enchantments.BLAST_PROTECTION && enchantment != Enchantments.PROJECTILE_PROTECTION && enchantment != Enchantments.PROTECTION;
    }
}
