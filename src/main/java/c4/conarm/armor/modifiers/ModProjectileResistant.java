package c4.conarm.armor.modifiers;

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
