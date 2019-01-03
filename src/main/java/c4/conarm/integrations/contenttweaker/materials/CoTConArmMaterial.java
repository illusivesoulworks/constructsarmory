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
