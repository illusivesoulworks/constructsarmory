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
