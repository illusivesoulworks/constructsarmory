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

package c4.conarm.integrations.tinkertoolleveling;

import c4.conarm.lib.armor.ArmorCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.toolleveling.ToolLevelNBT;

import javax.annotation.Nonnull;

/*
 * Base code is from Tinkers' Tool Leveling by boni
 * Tinkers' Tool Leveling is open source and distributed under the MIT License
 * View the source code on github: https://github.com/SlimeKnights/TinkersToolLeveling
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
public class CommandLevelArmor extends CommandBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Nonnull
    @Override
    public String getName() {
        return "levelupArmor";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/levelupArmor while holding a tinker armor in your hand";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        ItemStack itemStack = player.getHeldItemMainhand();

        if(!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorCore) {
            int xp;
            if(args.length > 0) {
                xp = parseInt(args[0]);
            }
            else {
                ToolLevelNBT data = new ToolLevelNBT(TinkerUtil.getModifierTag(itemStack, ModArmorLeveling.modArmorLeveling.getModifierIdentifier()));
                xp = ModArmorLeveling.modArmorLeveling.getXpForLevelup(data.level);
            }
            ModArmorLeveling.modArmorLeveling.addXp(itemStack, xp, player);
        }
        else {
            throw new CommandException("No tinker armor in hand");
        }
    }
}
