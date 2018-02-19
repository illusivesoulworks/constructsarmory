package c4.conarm.proxy;

import c4.conarm.client.LayerConstructArmor;
import c4.conarm.client.ModelConstructArmor;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.CustomFontRenderer;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.ToolClientEvents;

import java.util.*;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private static final ResourceLocation MODEL_ArmorForge = new ResourceLocation(ConstructsArmory.MODID, "block/armorforge");
    private static final ArmorModelLoader loader = new ArmorModelLoader();
    private static final String LOCATION_ArmorForge = "conarm:armorforge";
    private static final ModelResourceLocation locArmorForge = new ModelResourceLocation(LOCATION_ArmorForge, "normal");
//    public static Map<String, ModelConstructArmor> smallModels;
//    public static Map<String, ModelConstructArmor> models;
//    public static Map<String, TextureAtlasSprite> sprites;

    @Override
    public void preInit(FMLPreInitializationEvent evt) {
        super.preInit(evt);
        ModelLoaderRegistry.registerLoader(loader);
        ArmoryBook.init();
    }

    @Override
    public void init(FMLInitializationEvent evt) {
        super.init(evt);
        ArmoryRegistryClient.registerArmorBuildInfo();
    }

    @Override
    public void postInit(FMLPostInitializationEvent evt) {
        super.postInit(evt);
        LayerConstructArmor.calculateTextureSize();
        editRenderLayers();
//        initModels();
        ArmoryBook.INSTANCE.fontRenderer = TinkerBook.INSTANCE.fontRenderer;
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {

        //Armor Forge
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ConstructsArmor.armorForge), 0, locArmorForge);

        //Book
        ModelLoader.setCustomModelResourceLocation(ConstructsArmor.book, 0, new ModelResourceLocation(ConstructsArmor.book.getRegistryName(), "inventory"));

        //Armor Parts
        for (ArmorPart armorPart : ArmoryRegistry.armorParts) {
            ModelRegisterUtil.registerPartModel(armorPart);
        }

        //Armor Pieces
        for (ArmorCore armor : ArmoryRegistry.armor) {
            ArmorModelUtils.registerArmorModel(armor);
        }
    }

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre evt) {
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_main"));
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_trim"));
    }

//    @SubscribeEvent
//    public static void textureStitchAfter(TextureStitchEvent.Post evt) {
//        sprites = new HashMap<>();
//        sprites.putAll(CustomTextureCreator.sprites.get("conarm:models/armor/armor_main"));
//    }

    @SubscribeEvent (priority = EventPriority.LOW)
    public static void modelBake(ModelBakeEvent evt) {
        ToolClientEvents.replaceTableModel(locArmorForge, MODEL_ArmorForge, evt);
    }

    private void editRenderLayers() {
        Minecraft mc = Minecraft.getMinecraft();
        RenderManager manager = mc.getRenderManager();
        Map<String, RenderPlayer> renderPlayerMap = manager.getSkinMap();
        for(RenderPlayer render : renderPlayerMap.values()) {
            render.addLayer(new LayerConstructArmor(render));
        }
        Render<?> render = manager.getEntityClassRenderObject(EntityArmorStand.class);
        ((RenderLivingBase<?>) render).addLayer(new LayerConstructArmor((RenderLivingBase<?>) render));
    }

//    private void initModels() {
//        models = new HashMap<>();
//        smallModels = new HashMap<>();
//        for (String identifier : sprites.keySet()) {
//            TextureAtlasSprite sprite = sprites.get(identifier);
//            smallModels.put(identifier, new ModelConstructArmor(0.5F, LayerConstructArmor.textureMapWidth, LayerConstructArmor.textureMapHeight, sprite.getOriginX(), sprite.getOriginY()));
//            models.put(identifier, new ModelConstructArmor(1.0F, LayerConstructArmor.textureMapWidth, LayerConstructArmor.textureMapHeight, sprite.getOriginX(), sprite.getOriginY() + 32));
//        }
//    }
}
