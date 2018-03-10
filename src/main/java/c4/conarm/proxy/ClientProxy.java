package c4.conarm.proxy;

import c4.conarm.armor.ClientArmorEvents;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.ArmoryRegistryClient;
import c4.conarm.ConstructsArmory;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.client.ArmorModelLoader;
import c4.conarm.client.ArmorModelUtils;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.book.ArmoryBook;
import c4.conarm.lib.client.KeyInputEvent;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.tools.ToolClientEvents;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private static final ResourceLocation MODEL_ArmorForge = new ResourceLocation(ConstructsArmory.MODID, "block/armorforge");
    private static final ArmorModelLoader loader = new ArmorModelLoader();
    private static final String LOCATION_ArmorForge = "conarm:armorforge";
    private static final ModelResourceLocation locArmorForge = new ModelResourceLocation(LOCATION_ArmorForge, "normal");

    @Override
    public void preInit(FMLPreInitializationEvent evt) {
        super.preInit(evt);
        ModelLoaderRegistry.registerLoader(loader);
        ArmoryBook.init();
        KeyInputEvent.init();
    }

    @Override
    public void init(FMLInitializationEvent evt) {
        super.init(evt);
        ArmoryRegistryClient.registerArmorBuildInfo();
        MinecraftForge.EVENT_BUS.register(new KeyInputEvent());
        MinecraftForge.EVENT_BUS.register(new ClientArmorEvents());
    }

    @Override
    public void postInit(FMLPostInitializationEvent evt) {
        super.postInit(evt);
        ArmoryBook.INSTANCE.fontRenderer = TinkerBook.INSTANCE.fontRenderer;
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {

        //Armor Forge
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ConstructsArmor.armorForge), 0, locArmorForge);

        //Armor Parts
        for (ArmorPart armorPart : ArmoryRegistry.armorParts) {
            ModelRegisterUtil.registerPartModel(armorPart);
        }

        //Armor Pieces
        for (ArmorCore armor : ArmoryRegistry.armor) {
            ArmorModelUtils.registerArmorModel(armor);
        }

        //All other items
        ConstructsArmor.initModels();
    }

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre evt) {
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_core"));
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_plates"));
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_trim"));
    }

    @SubscribeEvent (priority = EventPriority.LOW)
    public static void modelBake(ModelBakeEvent evt) {
        ToolClientEvents.replaceTableModel(locArmorForge, MODEL_ArmorForge, evt);
    }
}
