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

package c4.conarm.debug;

import c4.conarm.ConstructsArmory;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.armor.ArmorCore;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class DebugCommand extends CommandBase {

    public DebugCommand(){
        aliases = Lists.newArrayList(ConstructsArmory.MODID, "DB", "db");
    }

    private final List<String> aliases;

    @Override
    @Nonnull
    public String getName() {
        return "db";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "db";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {

        if (!(sender instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        ItemStack itemStack = player.getHeldItemMainhand();

        if(!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorCore) {
            int damage;
            if(args.length > 0) {
                damage = parseInt(args[0]);
            }
            else {
                damage = ToolHelper.getCurrentDurability(itemStack) - 1;
            }
            ArmorHelper.damageArmor(itemStack, DamageSource.OUT_OF_WORLD, damage, player, EntityLiving.getSlotForItemStack(itemStack).getIndex());
        }
        else {
            throw new CommandException("No tinker armor in hand");
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}