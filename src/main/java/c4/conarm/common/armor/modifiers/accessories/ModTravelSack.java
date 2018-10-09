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

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.ConstructsArmory;
import c4.conarm.client.utils.GuiHandler;
import c4.conarm.client.models.accessories.ModelKnapsack;
import c4.conarm.lib.utils.ConstructUtils;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IAccessoryRender;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public class ModTravelSack extends AccessoryModifier implements IAccessoryRender {

    public static final int SACK_SIZE = 27;

    @SideOnly(Side.CLIENT)
    private static ModelKnapsack model;
    private static ResourceLocation texture = ConstructUtils.getResource("textures/models/accessories/travel_sack.png");

    private static final String TAG_KNAPSACK = "knapsack";

    public ModTravelSack() {
        super("travel_sack");
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);
        NBTTagCompound oldSack = modifierTag.getCompoundTag(TAG_KNAPSACK);
        if (oldSack.isEmpty()) {
            modifierTag.setTag(TAG_KNAPSACK, (new ItemStackHandler(SACK_SIZE)).serializeNBT());
        } else {
            modifierTag.setTag(TAG_KNAPSACK, oldSack);
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST && super.canApplyCustom(stack);
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        openKnapsack(player);
    }

    private void openKnapsack(EntityPlayer player) {
        player.openGui(ConstructsArmory.instance, GuiHandler.GUI_KNAPSACK_ID, player.world, 0, 0, 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onAccessoryRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (model == null) {
            model = new ModelKnapsack();
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public static class KnapsackData extends ModifierNBT {

        public ItemStackHandler knapsack = new ItemStackHandler(SACK_SIZE);

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            knapsack.deserializeNBT((NBTTagCompound) tag.getTag(TAG_KNAPSACK));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setTag(TAG_KNAPSACK, knapsack.serializeNBT());
        }
    }
}
