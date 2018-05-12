package c4.conarm.common.armor.modifiers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class ModFireResistant extends AbstractModResistant {

    public ModFireResistant() {
        super("fire_resistant", 0xea9e32, Enchantments.FIRE_PROTECTION);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {

        return enchantment != Enchantments.BLAST_PROTECTION && enchantment != Enchantments.PROJECTILE_PROTECTION && enchantment != Enchantments.PROTECTION;
    }
}
