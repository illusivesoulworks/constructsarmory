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

import c4.conarm.client.models.accessories.ModelBelt;
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
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ModTravelBelt extends AccessoryModifier implements IAccessoryRender {

    private static final String TAG_HOTBAR = "hotbar";

    @SideOnly(Side.CLIENT)
    private static ModelBelt model;
    private static ResourceLocation texture = ConstructUtils.getResource("textures/models/accessories/travel_belt.png");

    public ModTravelBelt() {
        super("travel_belt");
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        swapHotbars(armor, player);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);
        NBTTagCompound oldBelt = modifierTag.getCompoundTag(TAG_HOTBAR);
        if (oldBelt.isEmpty()) {
            modifierTag.setTag(TAG_HOTBAR, (new ItemStackHandler(9)).serializeNBT());
        } else {
            modifierTag.setTag(TAG_HOTBAR, oldBelt);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onAccessoryRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (model == null) {
            model = new ModelBelt();
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    private void swapHotbars(ItemStack stack, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        HotbarData data = modtag.getTagData(HotbarData.class);

        ItemStackHandler belt = data.hotbar;

        for (int i = 0; i < belt.getSlots(); i++) {
            ItemStack heldStack = player.inventory.getStackInSlot(i).copy();
            ItemStack beltStack = belt.getStackInSlot(i).copy();
            player.inventory.setInventorySlotContents(i, beltStack);
            belt.setStackInSlot(i, heldStack);
        }

        modtag.save();
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS && super.canApplyCustom(stack);
    }

    public static class HotbarData extends ModifierNBT {

        ItemStackHandler hotbar = new ItemStackHandler(9);

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            hotbar.deserializeNBT((NBTTagCompound) tag.getTag(TAG_HOTBAR));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setTag(TAG_HOTBAR, hotbar.serializeNBT());
        }
    }
}
