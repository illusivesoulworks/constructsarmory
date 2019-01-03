/*
 * Copyright (c) 2018-2019 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.Util;

import java.util.List;

public class TraitMundane extends AbstractArmorTraitLeveled {

    private static final float MODIFIER = 0.2F;

    public TraitMundane(int lvl) {
        super("mundane", 0x424242, 3, lvl);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) source.getImmediateSource();
            if (entityLiving.getHeldItemMainhand().isEmpty()) {
                mods.addEffectiveness(MODIFIER * levels);
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
