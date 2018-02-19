package c4.conarm.armor.modifiers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class ModResistant extends AbstractModResistant {

    public ModResistant() {
        super("resistant", 0xfff6f6, Enchantments.PROTECTION);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {

        return enchantment != Enchantments.FIRE_PROTECTION && enchantment != Enchantments.PROJECTILE_PROTECTION && enchantment != Enchantments.BLAST_PROTECTION;
    }
}
