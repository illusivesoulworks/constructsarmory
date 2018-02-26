package c4.conarm.armor.common;

import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.collect.ImmutableSet;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nonnull;
import java.util.Set;

public class RepairRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    public RepairRecipe() {
        this.setRegistryName(ConstructUtils.getResource("repair"));
    }

    private static final Set<Item> repairItems = ImmutableSet.of(ConstructsArmor.polishingKit);

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
