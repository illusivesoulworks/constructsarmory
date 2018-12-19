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

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import c4.conarm.lib.utils.ConstructUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.potion.TinkerPotion;

public class TraitSuperhot extends AbstractArmorTrait {

    public static TinkerPotion superhotPotion = new SuperhotPotion();

    public TraitSuperhot() {
        super("superhot", 0xffffff);
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        if (player.isBurning()) {
            superhotPotion.apply(player, 25, (int) ArmorHelper.getArmorAbilityLevel(player, this.getIdentifier()));
        }
    }

    private static class SuperhotPotion extends TinkerPotion {

        public static final String UUID = "da1c3163-029c-4b7f-974b-de9a6dcf3c00";

        public SuperhotPotion() {
            super(ConstructUtils.getResource("superhotPotion"), false, false);
            this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID, 0.15D, 2);
        }

        public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier)
        {
            return modifier.getAmount() * (double)(amplifier);
        }
    }
}
