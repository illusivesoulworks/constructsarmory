package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class TraitAutoforge extends AbstractArmorTrait {

    public TraitAutoforge() {
        super("autoforge", 0xffffff);;
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        if (source.isFireDamage()) {
            if (source == DamageSource.LAVA) {
                ArmorHelper.healArmor(armor, 3, player, slot);
            } else {
                ArmorHelper.healArmor(armor, 1, player, slot);
            }
            return 0;
        }
        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }
}
