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
