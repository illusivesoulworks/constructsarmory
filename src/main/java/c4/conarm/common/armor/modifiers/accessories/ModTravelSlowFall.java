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

import c4.conarm.client.models.accessories.ModelCloak;
import c4.conarm.lib.modifiers.IAccessoryRender;
import c4.conarm.lib.utils.ConstructUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTravelSlowFall extends AbstractToggleAccessoryModifier implements IAccessoryRender {

    @SideOnly(Side.CLIENT)
    private static ModelCloak model;
    private static ResourceLocation texture = ConstructUtils.getResource("textures/models/accessories/travel_cloak.png");

    public ModTravelSlowFall() {
        super("travel_slowfall", true);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (getToggleData(armor).toggle && player.motionY < 0) {
            player.motionY /= 1.5D;
            player.fallDistance = 0.0F;
        }

        super.onArmorTick(armor, world, player);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST && super.canApplyCustom(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onAccessoryRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (model == null) {
            model = new ModelCloak();
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
