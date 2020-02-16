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

package c4.conarm.common;

import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.utils.ConstructUtils;
import com.google.common.collect.ImmutableSet;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nonnull;
import java.util.Set;

public class RepairRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public RepairRecipe() {
        this.setRegistryName(ConstructUtils.getResource("repair"));
    }

    private static final Set<Item> repairItems = ImmutableSet.of(ConstructsRegistry.polishingKit);

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        return !getRepairedTool(inv, true).isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return getRepairedTool(inv, true);
    }

    @Nonnull
    private ItemStack getRepairedTool(@Nonnull InventoryCrafting inv, boolean simulate) {

        ItemStack armor = null;
        NonNullList<ItemStack> input = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack slot = inv.getStackInSlot(i);

            if(slot.isEmpty()) {
                continue;
            }

            slot = slot.copy();
            slot.setCount(1);

            Item item = slot.getItem();

            if(item instanceof TinkersArmor) {

                if(armor != null) {
                    return ItemStack.EMPTY;
                }
                armor = slot;
            }

            else if(repairItems.contains(item)) {
                input.set(i, slot);
            }

            else {
                return ItemStack.EMPTY;
            }
        }

        if(armor == null) {
            return ItemStack.EMPTY;
        }

        if(simulate) {
            input = Util.deepCopyFixedNonNullList(input);
        }

        return ((TinkersArmor) armor.getItem()).repair(armor.copy(), input);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

}
