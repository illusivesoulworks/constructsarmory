package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
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
