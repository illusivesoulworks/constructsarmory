/*
 * Copyright (c) 2018 <C4>
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

package c4.conarm.lib.modifiers;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.traits.AbstractArmorTrait;
import c4.conarm.lib.utils.RecipeMatchHolder;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class ArmorModifierTrait extends AbstractArmorTrait implements IModifierDisplay, IArmorModelModifier {

    protected final int maxLevel;

    public ArmorModifierTrait(String identifier, int color) {
        this(identifier, color, 0, 0);
    }

    public ArmorModifierTrait(String identifier, int color, int maxLevel, int countPerLevel) {
        super(identifier, color);

        // register the modifier trait
        TinkerRegistry.addTrait(this);
        ArmoryRegistry.registerModifier(this);

        this.maxLevel = maxLevel;
        this.aspects.clear();

        if(maxLevel > 0 && countPerLevel > 0) {
            addAspects(new ModifierAspect.MultiAspect(this, color, maxLevel, countPerLevel, 1));
        }
        else {
            if(maxLevel > 0) {
                addAspects(new ModifierAspect.LevelAspect(this, maxLevel));
            }
            addAspects(new ModifierAspect.DataAspect(this, color));
            addAspects(ModifierAspect.freeModifier);
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        if (!(stack.getItem() instanceof TinkersArmor)) {
            return false;
        }
        else if(super.canApplyCustom(stack)) {
            return true;
        }
        // no max level
        else if(maxLevel == 0) {
            return false;
        }

        // already present, limit by level
        NBTTagCompound tag = TinkerUtil.getModifierTag(stack, identifier);

        return ModifierNBT.readTag(tag).level <= maxLevel;
    }


    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        if(maxLevel > 0) {
            return getLeveledTooltip(modifierTag, detailed);
        }
        return super.getTooltip(modifierTag, detailed);
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public List<List<ItemStack>> getItems() {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        Optional<PriorityQueue<RecipeMatch>> recipes = RecipeMatchHolder.getRecipes(this);
        if (recipes.isPresent()) {
            PriorityQueue<RecipeMatch> recipeMatches = recipes.get();
            for (RecipeMatch rm : recipeMatches) {
                List<ItemStack> in = rm.getInputs();
                if (!in.isEmpty()) {
                    builder.add(in);
                }
            }
        }

        return builder.build();
    }
}
