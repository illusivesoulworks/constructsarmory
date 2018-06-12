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

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.network.SetStepHeightPacket;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModHighStride extends ArmorModifierTrait {

    public ModHighStride() {
        super("high_stride", 0x8c8c8c, 2, 0);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        NBTTagCompound tag = TinkerUtil.getModifierTag(armor, identifier);
        ModifierNBT data = ModifierNBT.readTag(tag);
        if (player instanceof EntityPlayerMP) {
            TinkerNetwork.sendTo(new SetStepHeightPacket(data.level + 0.6F), (EntityPlayerMP) player);
        }
    }

    @Override
    public void onArmorChanged(ItemStack armor, EntityPlayer player, int slot) {
        if (player instanceof EntityPlayerMP) {
            TinkerNetwork.sendTo(new SetStepHeightPacket(0.6F), (EntityPlayerMP) player);
        }
    }
}
