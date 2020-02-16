/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;

public abstract class TraitProgressiveArmorStats extends AbstractArmorTrait {

    protected final String pool_key;
    protected final String applied_key;

    public TraitProgressiveArmorStats(String identifier, TextFormatting color) {
        super(identifier, color);

        pool_key = identifier + "StatPool";
        applied_key = identifier + "StatBonus";
    }

    public TraitProgressiveArmorStats(String identifier, int color) {
        super(identifier, color);

        pool_key = identifier + "StatPool";
        applied_key = identifier + "StatBonus";
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        StatNBT bonus = getBonus(rootCompound);

        data.durability += bonus.durability;
        data.defense += bonus.defense;
        data.toughness += bonus.toughness;

        TagUtil.setToolTag(rootCompound, data.get());
    }

    protected boolean hasPool(NBTTagCompound root) {
        return TagUtil.getExtraTag(root).hasKey(pool_key);
    }

    protected StatNBT getPool(NBTTagCompound root) {
        return getStats(root, pool_key);
    }

    protected void setPool(NBTTagCompound root, StatNBT data) {
        setStats(root, data, pool_key);
    }

    protected StatNBT getBonus(NBTTagCompound root) {
        return getStats(root, applied_key);
    }

    protected void setBonus(NBTTagCompound root, StatNBT data) {
        setStats(root, data, applied_key);
    }

    protected static StatNBT getStats(NBTTagCompound root, String key) {
        return ModifierNBT.readTag(TagUtil.getTagSafe(TagUtil.getExtraTag(root), key), StatNBT.class);
    }

    protected static void setStats(NBTTagCompound root, StatNBT data, String key) {
        NBTTagCompound extra = TagUtil.getExtraTag(root);
        NBTTagCompound tag = new NBTTagCompound();
        data.write(tag);
        extra.setTag(key, tag);
        TagUtil.setExtraTag(root, extra);
    }

    public static class StatNBT extends ModifierNBT {

        public int durability;
        public float defense;
        public float toughness;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            durability = tag.getInteger("durability");
            defense = tag.getFloat("defense");
            toughness = tag.getFloat("toughness");
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setInteger("durability", durability);
            tag.setFloat("defense", defense);
            tag.setFloat("toughness", toughness);
        }
    }
}
