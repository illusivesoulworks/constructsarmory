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

package c4.conarm.proxy;

import c4.conarm.ConstructsArmory;
import c4.conarm.client.events.ClientArmorEvents;
import c4.conarm.client.fx.ParticleSoul;
import c4.conarm.client.gui.PreviewPlayer;
import c4.conarm.client.layers.LayerAccessories;
import c4.conarm.client.utils.ArmorModelLoader;
import c4.conarm.client.utils.ArmorModelUtils;
import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.ArmoryRegistryClient;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.lib.book.ArmoryBook;
import c4.conarm.lib.utils.ConstructUtils;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
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
import java.util.Random;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private static final ArmorModelLoader loader = new ArmorModelLoader();
    private static final String LOCATION_ArmorForge = "conarm:armorforge";
    private static final String LOCATION_ArmorStation = "conarm:armorstation";
    private static final ModelResourceLocation locArmorForge = new ModelResourceLocation(LOCATION_ArmorForge, "normal");
    private static final ModelResourceLocation locArmorStation = new ModelResourceLocation(LOCATION_ArmorStation, "normal");
    private static final Random random = new Random();
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
        CustomTextureCreator.registerTexture(ConstructUtils.getResource("models/armor/broken_armor_core"));
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
        ToolClientEvents.replaceTableModel(locArmorForge, evt);
        ToolClientEvents.replaceTableModel(locArmorStation, evt);
    }

    @Override
    public void generateParticle(Entity entity) {
        if (entity instanceof EntityLivingBase && random.nextInt(5) == 0) {
            Particle soul = new ParticleSoul(entity.world, entity.posX, entity.posY + entity.height / 1.25D, entity.posZ, (float) Math.sqrt(((EntityLivingBase) entity).getHealth()));
            soul.setAlphaF(0.35F);
            Minecraft.getMinecraft().effectRenderer.addEffect(soul);
        }
    }
}
