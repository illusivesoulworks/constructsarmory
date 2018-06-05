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
