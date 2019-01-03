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
