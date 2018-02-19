package c4.conarm.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

public class TraitRough extends AbstractArmorTrait {

    public TraitRough() {
        super("rough", TextFormatting.AQUA);
    }

    private double calcAttack(ItemStack armor) {
        int durability = ToolHelper.getCurrentDurability(armor);
        int maxDurability = ToolHelper.getMaxDurability(armor);

        return Math.log((maxDurability - durability) / 72d + 1d) * 2;
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (source.getImmediateSource() instanceof EntityLivingBase) {
            attackEntitySecondary(new EntityDamageSource("roughArmor", player), (float) calcAttack(armor), source.getImmediateSource(), true, false);
        }
        return newDamage;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(calcAttack(tool))));
    }
}
