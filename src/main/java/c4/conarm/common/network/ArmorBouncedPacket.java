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

package c4.conarm.common.network;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.common.armor.traits.TraitUtils;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.tinkering.TinkersArmor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.ByteBufUtils;
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
        ItemStack armor = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        if (armor.getItem() instanceof TinkersArmor && !ToolHelper.isBroken(armor)
                && TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), ArmorTraits.bouncy.getIdentifier())) {
            ArmorHelper.damageArmor(armor, DamageSource.FALL, (int) (1 + magnitude), player, EntityEquipmentSlot.FEET.getIndex());
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
