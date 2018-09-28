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

package c4.conarm.common;

import c4.conarm.ConstructsArmory;
import c4.conarm.integrations.tinkertoolleveling.ConfigSyncLevelingPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.TinkerNetwork;

@Config(modid = ConstructsArmory.MODID)
public class ConfigHandler {

    @Config.Name("Spawn With Book")
    @Config.Comment("Set to true to give an Armory Book to players who enter a world for the first time")
    public static boolean spawnWithBook = true;
    @Config.Name("Compact GUI")
    @Config.Comment("Set to true to disable the armor preview panel in the Armor Station/Forge GUI")
    public static boolean compactView = false;
    @Config.Name("Bouncy Trait Uses Durability")
    @Config.Comment("Set to true to have the Bouncy trait use durability for each bounce")
    public static boolean bounceDurability = true;

    public static final Leveling leveling = new Leveling();

    public static class Leveling {

        @Config.Name("Starting Modifier Amount")
        @Config.Comment("Reduces the amount of modifiers a newly built armor gets if the value is lower than the regular amount of modifiers the armor would have.")
        @Config.RequiresWorldRestart
        public int newArmorMinModifiers = 3;
        @Config.Name("Maximum Levels")
        @Config.Comment("Maximum achievable levels. If set to 0 or lower there is no upper limit.")
        @Config.RequiresWorldRestart
        public int maximumLevels = -1;
        @Config.Name("Base XP Requirement")
        @Config.Comment("Base XP needed for armor")
        @Config.RequiresWorldRestart
        public int baseXP = 100;
        @Config.Name("Leveling Multiplier")
        @Config.Comment("How much to multiply the experience needed for each level")
        @Config.RequiresWorldRestart
        public float levelMultiplier = 2.0F;

    }

    @Mod.EventBusSubscriber(modid = ConstructsArmory.MODID)
    private static class ConfigEventHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent evt) {
            if (evt.getModID().equals(ConstructsArmory.MODID)) {
                ConfigManager.sync(ConstructsArmory.MODID, Config.Type.INSTANCE);
            }
        }

        @SubscribeEvent
        public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent evt) {
            ConfigSyncLevelingPacket sync = new ConfigSyncLevelingPacket(leveling.newArmorMinModifiers, leveling.maximumLevels, leveling.baseXP, leveling.levelMultiplier);
            TinkerNetwork.sendTo(sync, (EntityPlayerMP) evt.player);
        }
    }
}
