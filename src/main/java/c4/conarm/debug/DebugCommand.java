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