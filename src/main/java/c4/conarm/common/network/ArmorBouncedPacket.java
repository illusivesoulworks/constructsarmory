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

package c4.conarm.common.network;

import c4.conarm.common.ConfigHandler;
import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.tinkering.TinkersArmor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.DamageSource;
import slimeknights.mantle.network.AbstractPacketThreadsafe;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class ArmorBouncedPacket extends AbstractPacketThreadsafe {

    private double magnitude;

    public ArmorBouncedPacket() {}

    public ArmorBouncedPacket(double y) {
        magnitude = y;
    }

    @Override
    public void handleClientSafe(NetHandlerPlayClient netHandler) {
        // only sent to server
        throw new UnsupportedOperationException("Serverside only");
    }

    @Override
    public void handleServerSafe(NetHandlerPlayServer netHandler) {

        EntityPlayer player = netHandler.player;
        player.fallDistance = 0;
        if (ConfigHandler.bounceDurability) {
            ItemStack armor = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if (armor.getItem() instanceof TinkersArmor && !ToolHelper.isBroken(armor)
                    && TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), ArmorTraits.bouncy.getIdentifier())) {
                ArmorHelper.damageArmor(armor, DamageSource.FALL, (int) (1 + magnitude), player, EntityEquipmentSlot.FEET.getIndex());
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        magnitude = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(magnitude);
    }
}
