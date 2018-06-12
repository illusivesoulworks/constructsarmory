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
import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitPetravidity extends AbstractArmorTrait {

    public TraitPetravidity() {
        super("petravidity", TextFormatting.RED);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        BlockPos pos = player.getPosition();
        for(BlockPos candidate : new BlockPos[]{pos, pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down()}) {
            if (player.world.getBlockState(candidate).getMaterial() == Material.ROCK) {
                mods.addEffectiveness(0.2F);
                if (random.nextFloat() < 0.1F && ToolHelper.getCurrentDurability(armor) < ToolHelper.getMaxDurability(armor)) {
                    ArmorHelper.healArmor(armor, 3, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                }
                return mods;
            }
        }
        return mods;
    }
}
