package c4.conarm;

import c4.conarm.armor.ConstructsArmor;
import c4.conarm.debug.DebugCommand;
import c4.conarm.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.pulsar.control.PulseManager;
import slimeknights.tconstruct.TConstruct;

@Mod(   modid = ConstructsArmory.MODID,
        name = ConstructsArmory.MODNAME,
        version = ConstructsArmory.MODVER,
        dependencies = "required-after:forge@[14.23.2.2611,);required-after:mantle@[1.12-1.3.1,);required-after:tconstruct@[1.12.2-2.9.1,)",
        acceptedMinecraftVersions = "[1.12.2, 1.13)",
        certificateFingerprint = "5d5b8aee896a4f5ea3f3114784742662a67ad32f")
public class ConstructsArmory {

    public static final String MODID = "conarm";
    public static final String MODNAME = "Construct's Armory";
    public static final String MODVER = "0.0.10-a";

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

//    @Mod.EventHandler
//    public void serverLoad(FMLServerStartingEvent evt) {
//        evt.registerServerCommand(new DebugCommand());
//    }
}
