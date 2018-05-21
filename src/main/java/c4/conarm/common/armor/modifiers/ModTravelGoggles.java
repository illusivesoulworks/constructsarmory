/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ModTravelGoggles extends AbstractTravelGoggles {

    public ModTravelGoggles() {
        super(VisionType.ZOOM);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent evt) {
        ItemStack stack = (evt.getEntity()).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        AbstractTravelGoggles.GogglesData data = modtag.getTagData(AbstractTravelGoggles.GogglesData.class);
        if (data.goggles) {
            evt.setNewfov(0.3F);
        }
    }
}
