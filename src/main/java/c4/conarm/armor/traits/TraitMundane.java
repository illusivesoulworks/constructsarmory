package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.Util;

import java.util.List;

public class TraitMundane extends AbstractArmorTrait {

    private static final float MODIFIER = 0.2F;

    public TraitMundane() {
        super("mundane", 0x424242);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) source.getImmediateSource();
            if (entityLiving.getHeldItemMainhand().isEmpty()) {
                mods.addEffectiveness(MODIFIER);
            }
        }
        return super.getModifications(player, mods, armor, source, damage, slot);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = Util.translate(LOC_Extra, getIdentifier());
        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(MODIFIER)));
    }
}
