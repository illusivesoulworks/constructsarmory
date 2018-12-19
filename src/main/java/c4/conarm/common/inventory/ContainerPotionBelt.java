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

package c4.conarm.common.inventory;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.common.armor.modifiers.accessories.ModTravelPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import javax.annotation.Nonnull;

public class ContainerPotionBelt extends Container {

    private final IItemHandler potions;
    private final int defaultMaxSize;

    public ContainerPotionBelt(IInventory playerInventory, ItemStack belt) {

        this.defaultMaxSize = Items.POTIONITEM.getItemStackLimit(ItemStack.EMPTY);

        ModifierTagHolder modtag = ModifierTagHolder.getModifier(belt, ArmorModifiers.modTravelPotion.getIdentifier());
        ModTravelPotion.PotionsData data = modtag.getTagData(ModTravelPotion.PotionsData.class);

        this.potions = data.potions;

        for (int j = 0; j < potions.getSlots(); ++j)
        {
            this.addSlotToContainer(new SlotPotion(potions, j, 26 + j * 18, 20));
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 109));
        }
    }

    public String getUnformattedText() {
        return Util.translate(String.format("%s.name", ConstructsRegistry.travelPotion.getTranslationKey()));
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {

        if ((slotId < potions.getSlots() && clickTypeIn == ClickType.PICKUP) || (slotId >= potions.getSlots() && clickTypeIn == ClickType.QUICK_MOVE)) {
            setPotionStackSize(4);
        }
        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        setPotionStackSize(defaultMaxSize);
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);

        if (slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < potions.getSlots()) {
                if (!this.mergePotionStack(itemstack1, potions.getSlots(), this.inventorySlots.size())) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, potions.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return ItemStack.EMPTY;
    }


    protected boolean mergePotionStack(ItemStack stack, int startIndex, int endIndex)
    {
        boolean flag = false;
        int i = endIndex - 1;

        if (!stack.isEmpty())
        {
            while (true)
            {
                if (i < startIndex)
                {
                    break;
                }

                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

                if (itemstack1.isEmpty() && slot1.isItemValid(stack))
                {
                    slot1.putStack(stack.splitStack(1));
                    slot1.onSlotChanged();
                    flag = true;
                    break;
                }

                --i;
            }
        }

        return flag;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(playerIn.getItemStackFromSlot(EntityEquipmentSlot.LEGS), ArmorModifiers.modTravelPotion.getIdentifier());
        ModTravelPotion.PotionsData data = modtag.getTagData(ModTravelPotion.PotionsData.class);
        for (int i = 0; i < this.potions.getSlots(); i++) {
            data.potions.setStackInSlot(i, this.potions.getStackInSlot(i).copy());
        }
        modtag.save();
    }

    protected static void setPotionStackSize(int amount) {
        Items.POTIONITEM.setMaxStackSize(amount);
        Items.SPLASH_POTION.setMaxStackSize(amount);
        Items.LINGERING_POTION.setMaxStackSize(amount);
    }
}
