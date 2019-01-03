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
