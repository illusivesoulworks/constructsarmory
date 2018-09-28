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

package c4.conarm.common.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.UUID;

public class TraitInvigorating extends AbstractArmorTrait {

    protected static final UUID[] HEALTH_MODIFIERS = new UUID[]{
            UUID.fromString("cc2c4cca-6d0c-4468-bca3-7994834be6cc"),
            UUID.fromString("abda444f-5876-4261-ba71-63c44fb9d581"),
            UUID.fromString("988afa5c-a093-4983-8f79-cb8ddcf77233"),
            UUID.fromString("1642212e-b5d5-4225-b1eb-1620edf6fbb9") };
    private static final double HEALTH_PER_LEVEL = 4.0D;

    public TraitInvigorating() {
        super("invigorating", TextFormatting.LIGHT_PURPLE);
    }

    @Override
    public void onArmorEquipped(ItemStack armor, EntityPlayer player, int slot) {
        IAttributeInstance healthAtt = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = healthAtt.getModifier(HEALTH_MODIFIERS[slot]);
        if (modifier == null) {
            healthAtt.applyModifier(new AttributeModifier(HEALTH_MODIFIERS[slot], "Invigorating trait modifier", HEALTH_PER_LEVEL, 0));
        }
    }

    @Override
    public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {
        IAttributeInstance healthAtt = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        AttributeModifier modifier = healthAtt.getModifier(HEALTH_MODIFIERS[slot]);
        if (modifier != null) {
            healthAtt.removeModifier(modifier);
        }
        player.setHealth(player.getHealth());
    }
}
