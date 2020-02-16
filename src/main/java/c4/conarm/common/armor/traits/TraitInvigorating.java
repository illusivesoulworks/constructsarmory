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

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class TraitInvigorating extends AbstractArmorTrait {

    protected static final UUID HEALTH_MODIFIER = UUID.fromString("cc2c4cca-6d0c-4468-bca3-7994834be6cc");
    private static final double HEALTH_PER_LEVEL = 4.0D;

    public TraitInvigorating() {
        super("invigorating", TextFormatting.LIGHT_PURPLE);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent evt) {

        if (evt.phase == TickEvent.Phase.END && evt.side == Side.SERVER) {
            EntityPlayer player = evt.player;
            IAttributeInstance healthAtt = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            AttributeModifier modifier = healthAtt.getModifier(HEALTH_MODIFIER);
            double oldMaxHealth = healthAtt.getAttributeValue();
            double level = ArmorHelper.getArmorAbilityLevel(player, this.identifier);

            if (modifier != null) {
                healthAtt.removeModifier(modifier);
            }

            if (level > 0) {
                healthAtt.applyModifier(new AttributeModifier(HEALTH_MODIFIER, "Invigorating trait modifier",
                        HEALTH_PER_LEVEL * level, 0));
            }

            if (oldMaxHealth > healthAtt.getAttributeValue() && player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }
}
