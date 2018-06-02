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
        dependencies = "required-after:forge@[14.23.2.2611,);required-after:mantle@[1.12-1.3.1,);required-after:tconstruct@[1.12.2-2.9.1,)",
        acceptedMinecraftVersions = "[1.12.2, 1.13)",
        certificateFingerprint = "5d5b8aee896a4f5ea3f3114784742662a67ad32f")
public class ConstructsArmory {

    public static final String MODID = "conarm";
    public static final String MODNAME = "Construct's Armory";
    public static final String MODVER = "0.0.19-b";

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
        evt.registerServerCommand(new DebugCommand());
        if (ModArmorLeveling.modArmorLeveling != null) {
            evt.registerServerCommand(new CommandLevelArmor());
        }
    }
}
