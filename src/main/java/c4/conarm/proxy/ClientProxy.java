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

package c4.conarm.proxy;

import c4.conarm.client.gui.PreviewPlayer;
import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.client.layers.LayerAccessories;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.ArmoryRegistryClient;
import c4.conarm.ConstructsArmory;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.client.utils.ArmorModelLoader;
import c4.conarm.client.utils.ArmorModelUtils;
import c4.conarm.lib.utils.ConstructUtils;
import c4.conarm.lib.book.ArmoryBook;
import c4.conarm.client.events.ClientArmorEvents;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
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
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.CustomTextureCreator;
import slimeknights.tconstruct.library.client.model.ModelHelper;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.ToolClientEvents;

import java.io.IOException;
import java.util.Map;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private static final ResourceLocation MODEL_ArmorForge = new ResourceLocation(ConstructsArmory.MODID, "block/armorforge");
    private static final ResourceLocation MODEL_ArmorStation = new ResourceLocation(ConstructsArmory.MODID, "block/armorstation");
    private static final ArmorModelLoader loader = new ArmorModelLoader();
    private static final String LOCATION_ArmorForge = "conarm:armorforge";
    private static final String LOCATION_ArmorStation = "conarm:armorstation";
    private static final ModelResourceLocation locArmorForge = new ModelResourceLocation(LOCATION_ArmorForge, "normal");
    private static final ModelResourceLocation locArmorStation = new ModelResourceLocation(LOCATION_ArmorStation, "normal");
    public static final Map<String, String> modifierCache = Maps.newHashMap();

    @Override
    public void preInit(FMLPreInitializationEvent evt) {
        super.preInit(evt);
        ModelLoaderRegistry.registerLoader(loader);
        ArmoryBook.init();
        ClientArmorEvents.init();
    }

    @Override
    public void init(FMLInitializationEvent evt) {
        super.init(evt);
        ArmoryRegistryClient.registerArmorBuildInfo();
        MinecraftForge.EVENT_BUS.register(new ClientArmorEvents());
        MinecraftForge.EVENT_BUS.register(new ClientArmorEvents());
    }

    @Override
    public void postInit(FMLPostInitializationEvent evt) {
        super.postInit(evt);
        Minecraft mc = Minecraft.getMinecraft();
        RenderManager manager = mc.getRenderManager();
        manager.entityRenderMap.put(PreviewPlayer.class, new RenderPlayer(manager));
        Map<String, RenderPlayer> renderPlayerMap = manager.getSkinMap();
        for(RenderPlayer render : renderPlayerMap.values()) {
            render.addLayer(new LayerAccessories<>(render));
        }
        ArmoryBook.INSTANCE.fontRenderer = TinkerBook.INSTANCE.fontRenderer;
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {

        //Armor Forge
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ConstructsRegistry.armorForge), 0, locArmorForge);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ConstructsRegistry.armorStation), 0, locArmorStation);

        //Armor Parts
        for (ArmorPart armorPart : ArmoryRegistry.armorParts) {
            ModelRegisterUtil.registerPartModel(armorPart);
        }

        //Armor Pieces
        for (ArmorCore armor : ArmoryRegistry.armor) {
            ArmorModelUtils.registerArmorModel(armor);
        }

        for (IModifier modifier : ArmoryRegistry.getAllArmorModifiers()) {
            if (modifier == TinkerModifiers.modCreative || modifier == ArmorModifiers.modExtraTrait) {
                continue;
            }

            ModelRegisterUtil.registerModifierModel(modifier, ConstructUtils.getResource("models/item/modifiers/" + modifier.getIdentifier()));
        }

        //All other items
        ConstructsRegistry.initModels();
    }

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre evt) {
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_core"));
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_plates"));
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/armor_trim"));
        try {
            Map<String, String> textureEntries = ModelHelper.loadTexturesFromJson(ConstructUtils.getResource("models/model_modifiers"));
            for (String s : textureEntries.keySet()) {
                IModifier mod = TinkerRegistry.getModifier(s);
                if (mod != null && mod.hasTexturePerMaterial()) {
                    CustomTextureCreator.registerTexture(ConstructUtils.getResource(textureEntries.get(s)));
                } else {
                    Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(ConstructUtils.getResource(textureEntries.get(s)));
                }
            }
            modifierCache.putAll(textureEntries);
        } catch (IOException e) {
            ConstructsArmory.logger.error("Could not load model modifiers {}", "models/model_modifiers");
        }
    }

    @SubscribeEvent (priority = EventPriority.LOW)
    public static void modelBake(ModelBakeEvent evt) {
        ToolClientEvents.replaceTableModel(locArmorForge, MODEL_ArmorForge, evt);
        ToolClientEvents.replaceTableModel(locArmorStation, MODEL_ArmorStation, evt);
    }
}
