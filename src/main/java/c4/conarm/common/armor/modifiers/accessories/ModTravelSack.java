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

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.ConstructsArmory;
import c4.conarm.client.models.accessories.ModelKnapsack;
import c4.conarm.client.utils.GuiHandler;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IAccessoryRender;
import c4.conarm.lib.utils.ConstructUtils;
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
