package c4.conarm.armor.modifiers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public class ModBlastResistant extends AbstractModResistant {

    public ModBlastResistant() {
        super("blast_resistant", 0xffaa23, Enchantments.BLAST_PROTECTION);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {

        return enchantment != Enchantments.FIRE_PROTECTION && enchantment != Enchantments.PROJECTILE_PROTECTION && enchantment != Enchantments.PROTECTION;
    }
}
