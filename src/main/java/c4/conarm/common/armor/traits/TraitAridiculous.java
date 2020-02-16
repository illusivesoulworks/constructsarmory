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

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class TraitAridiculous extends AbstractArmorTrait {

    public TraitAridiculous() {
        super("aridiculous", TextFormatting.DARK_RED);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        mods.addEffectiveness(calcAridiculousness(player.getEntityWorld(), player.getPosition()) / 10F);
        return mods;
    }

    private float calcAridiculousness(World world, BlockPos pos) {
        Biome biome = world.getBiomeForCoordsBody(pos);
        float rain = world.isRaining() ? biome.getRainfall() / 2F : 0F;
        return (float) (Math.pow(1.25, 3D * (0.5F + biome.getTemperature(pos) - biome.getRainfall())) - 1.25D) - rain;
    }
}
