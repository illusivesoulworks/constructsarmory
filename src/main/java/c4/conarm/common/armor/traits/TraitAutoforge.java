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

import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.IToolMod;

public class TraitAutoforge extends AbstractArmorTrait {

    public TraitAutoforge() {
        super("autoforge", 0xffffff);;
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {

        if (player.ticksExisted % 20 == 0) {
            IBlockState state = player.world.getBlockState(player.getPosition());

            if (player.isInLava()) {
                ArmorHelper.healArmor(tool, 3, player, EntityLiving.getSlotForItemStack(tool).getIndex());
            } else if (player.isBurning() || state.getBlock() instanceof BlockFire) {
                ArmorHelper.healArmor(tool, 1, player, EntityLiving.getSlotForItemStack(tool).getIndex());
            }
        }
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {

        if (source.isFireDamage()) {
            return 0;
        }

        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return !otherModifier.getIdentifier().equals(ArmorModifiers.modWaterwalk.getIdentifier()) && super.canApplyTogether(otherModifier);
    }
}
