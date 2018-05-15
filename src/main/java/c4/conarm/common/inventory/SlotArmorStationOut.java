/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;

/*This class is a re-implementation of the
SlotToolStationOut class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class SlotArmorStationOut extends Slot
{
    public ContainerArmorStation parent;

    public SlotArmorStationOut(final int index, final int xPosition, final int yPosition, final ContainerArmorStation container) {
        super(new InventoryCraftResult(), index, xPosition, yPosition);
        this.parent = container;
    }

    public boolean isItemValid(final ItemStack stack) {
        return false;
    }

    @Nonnull
    public ItemStack onTake(final EntityPlayer playerIn, @Nonnull final ItemStack stack) {
        FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, this.parent.getTile());
        this.parent.onResultTaken(playerIn, stack);
        stack.onCrafting(playerIn.getEntityWorld(), playerIn, 1);
        return super.onTake(playerIn, stack);
    }
}
