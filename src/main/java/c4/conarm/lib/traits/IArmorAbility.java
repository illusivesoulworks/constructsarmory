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

package c4.conarm.lib.traits;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public interface IArmorAbility {

    /**
     * Determines how the ability level used in {@link c4.conarm.lib.traits.IArmorTrait#onAbilityTick(int, World, EntityPlayer)}
     * is calculated
     * @param data The ModifierNBT data for the ItemStack that has the trait or modifier
     * @return The level amount for an instance of the trait or modifier on an ItemStack
     */
    int getAbilityLevel(ModifierNBT data);
}
