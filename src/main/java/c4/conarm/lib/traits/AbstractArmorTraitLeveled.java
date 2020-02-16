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

package c4.conarm.lib.traits;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public abstract class AbstractArmorTraitLeveled extends AbstractArmorTrait {

    protected final String name;
    protected final int levels;

    public AbstractArmorTraitLeveled(String identifier, int color, int maxLevels, int levels) {
        this(identifier, String.valueOf(levels), color, maxLevels, levels);
    }

    public AbstractArmorTraitLeveled(String identifier, String suffix, int color, int maxLevels, int levels) {
        super(identifier + suffix, color);
        this.name = identifier + "_armor";

        this.levels = levels;

        IModifier modifier = TinkerRegistry.getModifier(name);
        if(modifier != null) {
            if(modifier instanceof AbstractArmorTraitLeveled && ((AbstractArmorTraitLeveled) modifier).levels > this.levels) {
                TinkerRegistry.registerModifierAlias(this, name);
            }
        }
        else {
            TinkerRegistry.registerModifierAlias(this, name);
        }

        aspects.clear();
        this.addAspects(new ModifierAspect.LevelAspect(this, maxLevels), new ModifierAspect.DataAspect(this, color));
    }

    @Override
    public void updateNBTforTrait(NBTTagCompound modifierTag, int newColor) {
        super.updateNBTforTrait(modifierTag, newColor);

        ModifierNBT data = ModifierNBT.readTag(modifierTag);
        data.level = 0; // handled by applyEffect in this case
        data.write(modifierTag);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);

        NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
        int index = TinkerUtil.getIndexInCompoundList(tagList, getModifierIdentifier());

        NBTTagCompound tag = new NBTTagCompound();
        if(index > -1) {
            tag = tagList.getCompoundTagAt(index);
        }
        else {
            index = tagList.tagCount();
            tagList.appendTag(tag);
        }
        if(!tag.getBoolean(identifier)) {
            ModifierNBT data = ModifierNBT.readTag(tag);
            data.level += levels;
            data.write(tag);
            tag.setBoolean(identifier, true);
            tagList.set(index, tag);
            TagUtil.setModifiersTagList(rootCompound, tagList);

            applyModifierEffect(rootCompound);
        }
    }

    /**
     * Called when the trait gets applied. Called for each application/level of the trait.
     * Only called once per specific trait (e.g. Writable1 and Writable2) but multiple times overall (per specific trait present)
     *
     * Unlike Modifiers that get applied with the total result, you can do things incrementally here.
     */
    public void applyModifierEffect(NBTTagCompound rootCompound) {

    }

    @Override
    public String getModifierIdentifier() {
        return name;
    }

    @Override
    public String getLocalizedName() {
        String locName = Util.translate(LOC_Name, name);
        if(levels > 1) {
            locName += " " + TinkerUtil.getRomanNumeral(levels);
        }
        return locName;
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(LOC_Desc, name);
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return getLeveledTooltip(modifierTag, detailed);
    }
}
