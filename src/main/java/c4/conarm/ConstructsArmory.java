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

package c4.conarm;

import c4.conarm.debug.DebugCommand;
import c4.conarm.integrations.tinkertoolleveling.CommandLevelArmor;
import c4.conarm.integrations.tinkertoolleveling.ModArmorLeveling;
import c4.conarm.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(   modid = ConstructsArmory.MODID,
        name = ConstructsArmory.MODNAME,
        version = ConstructsArmory.MODVER,
        dependencies =  "required-after:forge@[14.23.4.2745,);" +
                        "required-after:mantle@[1.12-1.3.1,);" +
                        "required-after:tconstruct@[1.12.2-2.13.0.171,);" +
                        "after:contenttweaker@(1.12.2-4.7.0,);" +
                        "after:crafttweaker@[1.12-4.1.9,)",
        acceptedMinecraftVersions = "[1.12.2, 1.13)",
        certificateFingerprint = "@FINGERPRINT@")
public class ConstructsArmory {

    public static final String MODID = "conarm";
    public static final String MODNAME = "Construct's Armory";
    public static final String MODVER = "1.2.5.3";

    private static final boolean DEBUG = false;

    @SidedProxy(clientSide = "c4.conarm.proxy.ClientProxy", serverSide = "c4.conarm.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ConstructsArmory instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        logger = evt.getModLog();
        proxy.preInit(evt);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        proxy.init(evt);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        proxy.postInit(evt);
    }

    @Mod.EventHandler
    public void onFingerPrintViolation(FMLFingerprintViolationEvent evt) {
        FMLLog.log.log(Level.ERROR, "Invalid fingerprint detected! The file " + evt.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent evt) {
        if (DEBUG) {
            evt.registerServerCommand(new DebugCommand());
        }
        if (ModArmorLeveling.modArmorLeveling != null) {
            evt.registerServerCommand(new CommandLevelArmor());
        }
    }
}
