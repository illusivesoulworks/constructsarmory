/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import slimeknights.tconstruct.library.Util;

import java.util.List;

public class TraitInfernal extends AbstractArmorTrait {

    private static final float MODIFIER = 0.2F;

    public TraitInfernal() {
        super("infernal", 0xff0000);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.getTrueSource() instanceof EntityLivingBase) {
            for (EnumCreatureType creatureType : EnumCreatureType.values()) {
                for (Biome.SpawnListEntry spawnListEntry : Biome.REGISTRY.getObject(new ResourceLocation("hell")).getSpawnableList(creatureType)) {
                    if (spawnListEntry.entityClass.equals(source.getTrueSource().getClass())) {
                        return super.getModifications(player, mods, armor, source, damage, slot);
                    }
                }
            }
        }
        mods.addEffectiveness(MODIFIER);
        return super.getModifications(player, mods, armor, source, damage, slot);
    }

    @Override
    public List<String> getExtraInfo(ItemStack armor, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(MODIFIER)));
    }
}
