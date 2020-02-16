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
