/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;

public class ModAntiMonsterResistance extends ArmorModifierTrait {

    protected final EnumCreatureAttribute type;

    private final float resistPerItem;

    public ModAntiMonsterResistance(String identifier, int color, int maxLevel, int countPerLevel, EnumCreatureAttribute type) {
        super(identifier, color, maxLevel, countPerLevel);
        this.type = type;

        resistPerItem = 0.1f / (float) countPerLevel;
    }

    protected float calcResistance(NBTTagCompound modifierTag) {
        ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);

        return (float) data.current * resistPerItem;
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        if (evt.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) evt.getSource().getTrueSource();
            if (entity.getCreatureAttribute() == type) {
                NBTTagCompound tag = TinkerUtil.getModifierTag(armor, this.identifier);
                evt.setAmount(evt.getAmount() * (1- calcResistance(tag)));
            }
        }
        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    @Override
    public List<String> getExtraInfo(ItemStack armor, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getIdentifier());

        if(I18n.canTranslate(loc)) {
            float dmg = calcResistance(modifierTag);
            return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(dmg)));
        }
        return super.getExtraInfo(armor, modifierTag);
    }
}
