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

package c4.conarm.client.events;

import c4.conarm.common.ConfigHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerClient {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onFoVChange(FOVUpdateEvent evt) {
        evt.setNewfov(getFoVModifier(evt.getEntity()));
    }

    private float getFoVModifier(EntityPlayer player) {
        float modifier = 1.0F;

        if (player.capabilities.isFlying) {
            modifier *= 1.1F;
        }

        IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        double speedModifier = ((iattributeinstance.getAttributeValue() /
                (double)player.capabilities.getWalkSpeed() + 1.0D) / 2.0D);

        if (ConfigHandler.fovCap >= 0) {
            speedModifier = Math.min(ConfigHandler.fovCap + 1.0D, speedModifier);
        }

        modifier = (float)((double)modifier * speedModifier);

        if (player.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(modifier) || Float.isInfinite(modifier)) {
            modifier = 1.0F;
        }

        if (player.isHandActive() && player.getActiveItemStack().getItem() == Items.BOW) {
            int i = player.getItemInUseMaxCount();
            float perc = (float)i / 20.0F;

            if (perc > 1.0F) {
                perc = 1.0F;
            } else {
                perc = perc * perc;
            }

            modifier *= 1.0F - perc * 0.15F;
        }

        return modifier;
    }
}
