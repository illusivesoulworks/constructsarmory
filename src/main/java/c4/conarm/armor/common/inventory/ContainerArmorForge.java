package c4.conarm.armor.common.inventory;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.modifiers.IArmorModifyable;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.armor.common.network.ArmorForgeSelectionPacket;
import c4.conarm.armor.common.network.ArmorForgeTextPacket;
import c4.conarm.client.GuiArmorForge;
import c4.conarm.armor.common.tileentities.TileArmorForge;
import c4.conarm.lib.utils.ArmorBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import slimeknights.mantle.inventory.BaseContainer;
import slimeknights.mantle.util.ItemStackList;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.IModifyable;
import slimeknights.tconstruct.library.tinkering.IRepairable;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.tools.common.inventory.ContainerTinkerStation;

import java.util.List;
import java.util.Set;

/*This class is a re-implementation of the
ContainerToolStation class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class ContainerArmorForge extends ContainerTinkerStation<TileArmorForge>{

    private final EntityPlayer player;
    protected SlotArmorForgeOut out;
    protected ArmorCore selectedArmor;
    protected int activeSlots;
    public String armorName;

    public ContainerArmorForge(final InventoryPlayer playerInventory, final TileArmorForge tile) {
        super(tile);
        int i;
        for (i = 0; i < tile.getSizeInventory(); i++) {
            this.addSlotToContainer(new SlotArmorForgeIn(tile, i, 0, 0, this));
        }
        this.addSlotToContainer(this.out = new SlotArmorForgeOut(i, 124, 38, this));
        this.addPlayerInventory(playerInventory, 8, 92);
        this.onCraftMatrixChanged(null);
        this.player = playerInventory.player;
    }

    public ItemStack getResult() {
        return this.out.getStack();
    }

    protected void syncNewContainer(final EntityPlayerMP player) {
        this.activeSlots = (this.tile).getSizeInventory();
        TinkerNetwork.sendTo(new ArmorForgeSelectionPacket(null, (this.tile).getSizeInventory()), player);
    }

    protected void syncWithOtherContainer(final BaseContainer<TileArmorForge> otherContainer, final EntityPlayerMP player) {
        this.syncWithOtherContainer((ContainerArmorForge) otherContainer, player);
    }

    protected void syncWithOtherContainer(final ContainerArmorForge otherContainer, final EntityPlayerMP player) {
        this.setArmorSelection(otherContainer.selectedArmor, otherContainer.activeSlots);
        this.setArmorName(otherContainer.armorName);
        TinkerNetwork.sendTo(new ArmorForgeSelectionPacket(otherContainer.selectedArmor, otherContainer.activeSlots), player);
        if (otherContainer.armorName != null && !otherContainer.armorName.isEmpty()) {
            TinkerNetwork.sendTo(new ArmorForgeTextPacket(otherContainer.armorName), player);
        }
    }

    public void setArmorSelection(final ArmorCore armor, int activeSlots) {
        if (activeSlots > (this.tile).getSizeInventory()) {
            activeSlots = (this.tile).getSizeInventory();
        }
        this.activeSlots = activeSlots;
        this.selectedArmor = armor;
        for (int i = 0; i < (this.tile).getSizeInventory(); ++i) {
            final Slot slot = this.inventorySlots.get(i);
            if (slot instanceof SlotArmorForgeIn) {
                final SlotArmorForgeIn slotArmorPart = (SlotArmorForgeIn) slot;
                slotArmorPart.setRestriction(null);
                if (i >= activeSlots) {
                    slotArmorPart.deactivate();
                }
                else {
                    slotArmorPart.activate();
                    if (armor != null) {
                        final List<PartMaterialType> pmts = armor.getArmorBuildComponents();
                        if (i < pmts.size()) {
                            slotArmorPart.setRestriction(pmts.get(i));
                        }
                    }
                }
                if (this.world.isRemote) {
                    slotArmorPart.updateIcon();
                }
            }
        }
    }

    public void setArmorName(final String name) {
        this.armorName = name;
        if (this.world.isRemote) {
            final GuiScreen screen = Minecraft.getMinecraft().currentScreen;
            if (screen instanceof GuiArmorForge) {
                ((GuiArmorForge)screen).textField.setText(name);
            }
        }
        if (this.out.getHasStack()) {
            if (name != null && !name.isEmpty()) {
                this.out.inventory.getStackInSlot(0).setStackDisplayName(name);
            }
            else {
                this.out.inventory.getStackInSlot(0).clearCustomName();
            }
        }
    }

    public void onCraftMatrixChanged(final IInventory inventoryIn) {
        this.updateGUI();
        try {
            ItemStack result = this.repairArmor(false);
            if (result.isEmpty()) {
                result = this.replaceArmorParts(false);
            }
            if (result.isEmpty()) {
                result = this.modifyArmor(false);
            }
            if (result.isEmpty()) {
                result = this.buildArmor();
            }
            this.out.inventory.setInventorySlotContents(0, result);
            this.updateGUI();
        }
        catch (TinkerGuiException e) {
            this.out.inventory.setInventorySlotContents(0, ItemStack.EMPTY);
            this.error(e.getMessage());
        }
        if (!this.world.isRemote) {
            final WorldServer server = (WorldServer)this.world;
            for (final EntityPlayer player : server.playerEntities) {
                if (player.openContainer != this && player.openContainer instanceof ContainerArmorForge && this.sameGui((BaseContainer)player.openContainer)) {
                    ((ContainerArmorForge)player.openContainer).out.inventory.setInventorySlotContents(0, this.out.getStack());
                }
            }
        }
    }

    public void onResultTaken(final EntityPlayer playerIn, final ItemStack stack) {
        boolean resultTaken = false;
        try {
            resultTaken = (!this.repairArmor(true).isEmpty() || !this.replaceArmorParts(true).isEmpty() || !this.modifyArmor(true).isEmpty());
        }
        catch (TinkerGuiException e) {
            e.printStackTrace();
        }
        if (resultTaken) {
            this.updateSlotsAfterArmorAction();
        }
        else {
            try {
                final ItemStack tool = this.buildArmor();
                if (!tool.isEmpty()) {
                    for (int i = 0; i < (this.tile).getSizeInventory(); ++i) {
                        (this.tile).decrStackSize(i, 1);
                    }
                    this.setArmorName("");
                }
            }
            catch (TinkerGuiException e) {
                e.printStackTrace();
            }
        }
        this.onCraftMatrixChanged(null);
        this.playCraftSound(playerIn);
    }

    protected void playCraftSound(final EntityPlayer player) {
        Sounds.playSoundForAll(player, SoundEvents.BLOCK_ANVIL_USE, 0.9f, 0.95f + 0.2f * TConstruct.random.nextFloat());
    }

    private ItemStack repairArmor(final boolean remove) {
        final ItemStack repairable = this.inventorySlots.get(0).getStack();
        if (repairable.isEmpty() || !(repairable.getItem() instanceof IRepairable)) {
            return ItemStack.EMPTY;
        }
        return ToolBuilder.tryRepairTool(this.getInputs(), repairable, remove);
    }

    private ItemStack replaceArmorParts(final boolean remove) throws TinkerGuiException {
        final ItemStack tool = this.inventorySlots.get(0).getStack();
        if (tool.isEmpty() || !(tool.getItem() instanceof TinkersArmor)) {
            return ItemStack.EMPTY;
        }
        final NonNullList<ItemStack> inputs = this.getInputs();
        final ItemStack result = ToolBuilder.tryReplaceToolParts(tool, inputs, remove);
        if (!result.isEmpty()) {
            TinkerCraftingEvent.ToolPartReplaceEvent.fireEvent(result, this.player, inputs);
        }
        return result;
    }

    private ItemStack modifyArmor(final boolean remove) throws TinkerGuiException {
        final ItemStack modifyable = this.inventorySlots.get(0).getStack();
        if (modifyable.isEmpty() || !(modifyable.getItem() instanceof IArmorModifyable)) {
            return ItemStack.EMPTY;
        }
        final ItemStack result = ArmorBuilder.tryModifyArmor(this.getInputs(), modifyable, remove);
        if (!result.isEmpty()) {
            TinkerCraftingEvent.ToolModifyEvent.fireEvent(result, this.player, modifyable.copy());
        }
        return result;
    }

    private ItemStack buildArmor() throws TinkerGuiException {
        final NonNullList<ItemStack> input = ItemStackList.withSize((this.tile).getSizeInventory());
        for (int i = 0; i < input.size(); ++i) {
            input.set(i, (this.tile).getStackInSlot(i));
        }
        final ItemStack result = ArmorBuilder.tryBuildArmor(input, this.armorName, this.getBuildableArmor());
        if (!result.isEmpty()) {
            TinkerCraftingEvent.ToolCraftingEvent.fireEvent(result, this.player, input);
        }
        return result;
    }

    protected Set<ArmorCore> getBuildableArmor() {
        return ArmoryRegistry.getArmorForgeCrafting();
    }

    private void updateSlotsAfterArmorAction() {
        (this.tile).setInventorySlotContents(0, ItemStack.EMPTY);
        for (int i = 1; i < (this.tile).getSizeInventory(); ++i) {
            if (!(this.tile).getStackInSlot(i).isEmpty() && (this.tile).getStackInSlot(i).getCount() == 0) {
                (this.tile).setInventorySlotContents(i, ItemStack.EMPTY);
            }
        }
    }

    private NonNullList<ItemStack> getInputs() {
        final NonNullList<ItemStack> input = NonNullList.withSize((this.tile).getSizeInventory() - 1, ItemStack.EMPTY);
        for (int i = 1; i < (this.tile).getSizeInventory(); ++i) {
            input.set(i - 1, (this.tile).getStackInSlot(i));
        }
        return input;
    }

    public boolean canMergeSlot(final ItemStack stack, final Slot slot) {
        return slot != this.out && super.canMergeSlot(stack, slot);
    }
}
