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

package c4.conarm.debug;

import c4.conarm.ConstructsArmory;
import c4.conarm.integrations.tinkertoolleveling.ModArmorLeveling;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.toolleveling.ModToolLeveling;
import slimeknights.toolleveling.ToolLevelNBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
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

//        List<String> info = Lists.newArrayList();
//
//        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData((EntityPlayer) sender);
//
//        if (armorAbilities != null) {
//            for (String identifier : armorAbilities.getAbilityMap().keySet()) {
//                info.add(String.format("\n%s: level %s", identifier, armorAbilities.getAbilityLevel(identifier)));
//            }
//        }
//
//        ConstructsArmory.logger.info(info);

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        ItemStack itemStack = player.getHeldItemMainhand();

        if(!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorCore) {
            int xp;
            if(args.length > 0) {
                xp = parseInt(args[0]);
            }
            else {
                ToolLevelNBT data = new ToolLevelNBT(TinkerUtil.getModifierTag(itemStack, ModArmorLeveling.modArmorLeveling.getModifierIdentifier()));
                xp = ModArmorLeveling.modArmorLeveling.getXpForLevelup(data.level, itemStack);
            }
            ModArmorLeveling.modArmorLeveling.addXp(itemStack, xp, player);
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