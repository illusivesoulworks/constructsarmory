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

package c4.conarm.integrations.contenttweaker.materials;

import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.TConMaterialRepresentation;
import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.Functions;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import stanhebben.zenscript.util.Pair;

import javax.annotation.Nonnull;
import java.util.List;

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
public class CoTConArmMaterial extends Material {

    public boolean hidden = false;
    public IItemStack representativeItem = null;
    public IItemStack shard = null;
    public ILiquidStack liquid = null;
    public String localizedName = null;
    public Functions.ItemLocalizer itemLocalizer = null;
    final TConMaterialRepresentation thisMaterial = new TConMaterialRepresentation(this);
    private final List<Pair<String, String>> traits;

    public CoTConArmMaterial(String identifier, int color, List<Pair<String, String>> traits) {
        super(identifier, color);
        this.traits = traits;
    }

    public void addItemMatch(RecipeMatch recipeMatch) {
        items.add(recipeMatch);
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public ItemStack getRepresentativeItem() {
        return CraftTweakerMC.getItemStack(representativeItem);
    }

    @Override
    public void setRepresentativeItem(ItemStack representativeItem) {
        this.representativeItem = CraftTweakerMC.getIItemStack(representativeItem);
    }

    @Override
    public void setShard(@Nonnull ItemStack stack) {
        this.shard = CraftTweakerMC.getIItemStack(stack);
    }

    @Override
    public ItemStack getShard() {
        return CraftTweakerMC.getItemStack(shard);
    }

    @Override
    public boolean hasFluid() {
        return liquid != null;
    }

    @Override
    public CoTConArmMaterial setFluid(Fluid liquid) {
        this.liquid = new MCLiquidStack(new FluidStack(liquid, 1));
        return this;
    }

    @Override
    public String getLocalizedName() {
        if (localizedName != null) {
            return localizedName;
        }
        return super.getLocalizedName();
    }

    @Override
    public String getLocalizedItemName(String itemName) {
        if (itemLocalizer != null) {
            return itemLocalizer.handle(thisMaterial, itemName);
        }
        return super.getLocalizedItemName(itemName);
    }

    @Override
    public Fluid getFluid() {
        return liquid == null ? null : CraftTweakerMC.getFluid(liquid.getDefinition());
    }

    public void registerTraits() {
        for (final Pair<String, String> traitPair : traits) {
            final String traitName = traitPair.getKey();
            final ITrait trait = TinkerRegistry.getTrait(traitName);
            if (trait != null) {
                this.addTrait(trait, traitPair.getValue());
            } else {
                CraftTweakerAPI.logError("Could not identify Trait <conarmtrait:" + traitName + ">, it will not be " +
                        "added to material " + getIdentifier());
            }
        }
    }

    void addTrait(String materialTrait, String dependency) {
        traits.add(new Pair<>(materialTrait, dependency));
    }
}
