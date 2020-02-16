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
        @Config.Name("XP Gain Cap")
        @Config.Comment("Maximum amount of XP that a single hit can give")
        @Config.RequiresWorldRestart
        public int xpGainCap = 100;
        @Config.Name("Damage to XP Multiplier")
        @Config.Comment("Multiplier to calculate xp from damage (e.g 0.25 means 25% of damage will be given as XP)")
        @Config.RequiresWorldRestart
        public float damageToXP = 0.25f;
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
            ConfigSyncLevelingPacket sync = new ConfigSyncLevelingPacket(
                leveling.newArmorMinModifiers,
                leveling.maximumLevels,
                leveling.baseXP,
                leveling.levelMultiplier,
                leveling.xpGainCap,
                leveling.damageToXP);
            TinkerNetwork.sendTo(sync, (EntityPlayerMP) evt.player);
        }
    }
}
