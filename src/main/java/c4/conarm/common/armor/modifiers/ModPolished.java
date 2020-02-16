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

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.client.IArmorMaterialTexture;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.utils.RecipeMatchHolder;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerAPIException;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModPolished extends ToolModifier implements IArmorMaterialTexture {

    public final Material material;

    public ModPolished(Material material) {
        super("polished_armor" + material.getIdentifier(), material.materialTextColor);

        if(!material.hasStats(ArmorMaterialType.PLATES)) {
            throw new TinkerAPIException(String.format("Trying to add a polished-modifier for a material without armor stats: %s", material.getIdentifier()));
        }

        this.material = material;
        addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this));

        ItemStack kit = ConstructsRegistry.polishingKit.getItemstackWithMaterial(material);
        ItemStack sand = new ItemStack(Blocks.SAND);
        RecipeMatchHolder.addRecipeMatch(this, new RecipeMatch.ItemCombination(1, kit, sand));
    }

    @Override
    public String getBaseTexture() {
        return "conarm:models/modifiers/mod_polished_armor_" + material.getIdentifier();
    }

    @Override
    public String getLocalizedName() {
        return Util.translate(LOC_Name, "polished_armor") + " (" + material.getLocalizedName() + ")";
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translateFormatted(String.format(LOC_Desc, "polished_armor"), material.getLocalizedName());
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        float originalToughness = ArmorTagUtil.getOriginalArmorStats(rootCompound).toughness;
        float matToughness = ((PlatesMaterialStats) material.getStats(ArmorMaterialType.PLATES)).toughness;
        float addedToughness = Math.max(0, matToughness - originalToughness);

        if (addedToughness > 0) {
            data.toughness += addedToughness;
            TagUtil.setToolTag(rootCompound, data.get());
        }

        NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound mod = tagList.getCompoundTagAt(i);
            ModifierNBT modData = ModifierNBT.readTag(mod);

            if(modData.identifier.equals(this.identifier)) {
                break;
            }

            if(modData.identifier.startsWith("polished_armor")) {
                tagList.removeTag(i);
                i--;
            }
        }

        TagUtil.setModifiersTagList(rootCompound, tagList);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {

        return stack.getItem() instanceof TinkersArmor;
    }

    @Override
    public boolean hasTexturePerMaterial() {
        return true;
    }

}
