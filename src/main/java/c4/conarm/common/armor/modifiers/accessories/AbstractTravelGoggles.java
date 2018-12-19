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

import c4.conarm.client.models.accessories.ModelGoggles;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IAccessoryRender;
import c4.conarm.lib.modifiers.IToggleable;
import c4.conarm.lib.utils.ConstructUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public abstract class AbstractTravelGoggles extends AccessoryModifier implements IAccessoryRender, IToggleable {

    private static final String TAG_GOGGLES = "goggles";

    @SideOnly(Side.CLIENT)
    private static ModelGoggles model;
    private static ResourceLocation texture = ConstructUtils.getResource("textures/models/accessories/travel_goggles.png");

    public VisionType visionType;

    public AbstractTravelGoggles(VisionType vision) {
        super(vision.identifier);
        this.visionType = vision;
    }

    public VisionType getVisionType() {
        return visionType;
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        toggleGoggles(armor);
    }

    private void toggleGoggles(ItemStack stack) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        data.goggles = !data.goggles;
        modtag.save();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onAccessoryRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (model == null) {
            model = new ModelGoggles();
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        float yaw = entityLivingBaseIn.prevRotationYawHead + (entityLivingBaseIn.rotationYawHead - entityLivingBaseIn.prevRotationYawHead) * partialTicks;
        float yawOffset = entityLivingBaseIn.prevRenderYawOffset + (entityLivingBaseIn.renderYawOffset - entityLivingBaseIn.prevRenderYawOffset) * partialTicks;
        float pitch = entityLivingBaseIn.prevRotationPitch + (entityLivingBaseIn.rotationPitch - entityLivingBaseIn.prevRotationPitch) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.rotate(yawOffset, 0, -1, 0);
        GlStateManager.rotate(yaw - 270, 0, 1, 0);
        GlStateManager.rotate(pitch, 0, 0, 1);
        GlStateManager.rotate(90, 0, -1, 0);
        model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean getToggleStatus(ItemStack stack) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        return data.goggles;
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && super.canApplyCustom(stack);
    }

    public static class GogglesData extends ModifierNBT {

        public boolean goggles = false;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            goggles = tag.getBoolean(TAG_GOGGLES);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setBoolean(TAG_GOGGLES, goggles);
        }
    }

    enum VisionType {

        ZOOM ("travel_goggles"),
        NIGHT_VISION ("night_vision"),
        SOUL ("soul_sight");

        String identifier;

        VisionType(String identifier) {
            this.identifier = identifier;
        }
    }
}
