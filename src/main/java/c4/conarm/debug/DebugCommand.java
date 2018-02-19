package c4.conarm.debug;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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

        List<String> info = Lists.newArrayList();
        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData((EntityPlayer) sender);

        if (armorAbilities != null) {
            for (String identifier : armorAbilities.getAbilityMap().keySet()) {
                info.add(String.format("\n%s: level %s", identifier, armorAbilities.getAbilityLevel(identifier)));
            }
        }

        ConstructsArmory.logger.info(info);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}