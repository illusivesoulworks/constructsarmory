package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;

public class TraitPrideful extends AbstractArmorTrait {

    private static final float MODIFIER = 0.1F;

    public TraitPrideful() {
        super("prideful", TextFormatting.DARK_PURPLE);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (player.getLastDamageSource() != null) {
            mods.addEffectiveness(MODIFIER);
            ArmorHelper.damageArmor(armor, source, 3, player, slot);
        }
        return mods;
    }
}
