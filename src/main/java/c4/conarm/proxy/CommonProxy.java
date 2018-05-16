/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.proxy;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.RepairRecipe;
import c4.conarm.common.blocks.BlockArmorForge;
import c4.conarm.common.blocks.BlockSoftObsidian;
import c4.conarm.common.tileentities.TileArmorForge;
import c4.conarm.common.armor.traits.TraitAquaspeed;
import c4.conarm.client.utils.GuiHandler;
import c4.conarm.common.events.PlayerDataEvents;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.ConstructsArmory;
import c4.conarm.common.events.ArmorEvents;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.materials.ArmorMaterials;
import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.common.network.ConstructsNetwork;
import c4.conarm.common.armor.traits.TraitSuperhot;
import c4.conarm.common.blocks.BlockArmorStation;
import c4.conarm.common.tileentities.TileArmorStation;
import c4.conarm.lib.utils.ConstructUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.TableRecipeFactory;
import slimeknights.tconstruct.tools.common.item.ItemBlockTable;

import java.util.Locale;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent evt) {

        ConstructsNetwork.init();
        ArmorMaterials.registerArmorMaterialStats();
    }

    public void init(FMLInitializationEvent evt) {

        ArmoryRegistry.registerAllArmorForging();
        ArmorMaterials.setupArmorMaterials();
        MinecraftForge.EVENT_BUS.register(new ArmorEvents());
        MinecraftForge.EVENT_BUS.register(new ArmorAbilityHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerDataEvents());
        CapabilityManager.INSTANCE.register(ArmorAbilityHandler.IArmorAbilities.class, new ArmorAbilityHandler.Storage() , ArmorAbilityHandler.ArmorAbilities::new);
        NetworkRegistry.INSTANCE.registerGuiHandler(ConstructsArmory.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent evt) {
        ArmorModifiers.registerPolishedModifiers();
        ArmorModifiers.registerExtraTraitModifiers();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        IForgeRegistry<Block> registry = evt.getRegistry();

        ConstructsRegistry.armorForge = ConstructUtils.registerBlock(registry, new BlockArmorForge(), "armorforge");
        ConstructsRegistry.armorStation = ConstructUtils.registerBlock(registry, new BlockArmorStation(), "armorstation");
        ConstructsRegistry.softObsidian = ConstructUtils.registerBlock(registry, new BlockSoftObsidian(), "soft_obsidian");

        GameRegistry.registerTileEntity(TileArmorStation.class, "armorstation");
        GameRegistry.registerTileEntity(TileArmorForge.class, "armorforge");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> registry = evt.getRegistry();

        ConstructsRegistry.registerArmorParts(registry);
        ConstructsRegistry.registerArmorPieces(registry);
        ConstructsRegistry.registerItems(registry);
        ArmorModifiers.setupModifiers();
        ConstructsRegistry.armorForge = ConstructUtils.registerItemBlock(registry, new ItemBlockTable(ConstructsRegistry.armorForge));
        ConstructsRegistry.armorStation = ConstructUtils.registerItemBlock(registry, new ItemBlockTable(ConstructsRegistry.armorStation));

        for(Pair<Item, ArmorPart> armorPartPattern : ConstructsRegistry.armorPartPatterns) {
            registerStencil(armorPartPattern.getLeft(), armorPartPattern.getRight());
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();

        if (ConstructsRegistry.armorForge != null) {
            ConstructsRegistry.armorForge.baseBlocks.addAll(TinkerTools.toolForge.baseBlocks);
            for (String oredict : ConstructsRegistry.armorForge.baseBlocks) {
                Block brick = TinkerSmeltery.searedBlock;
                if(brick == null) {
                    brick = Blocks.STONEBRICK;
                }

                TableRecipeFactory.TableRecipe recipe =
                        new TableRecipeFactory.TableRecipe(
                                new ResourceLocation(ConstructsArmory.MODID, "armorforge"),
                                new OreIngredient(oredict),
                                new ItemStack(ConstructsRegistry.armorForge),
                                CraftingHelper.parseShaped("BBB", "MTM", "M M",
                                        'B', brick,
                                        'M', oredict,
                                        'T', ConstructsRegistry.armorStation));
                recipe.setRegistryName("armorforge_" + oredict.toLowerCase(Locale.US));
                registry.register(recipe);
            }
        }

        registry.register(new RepairRecipe());
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt) {
        evt.getRegistry().registerAll(TraitSuperhot.superhotPotion, TraitAquaspeed.aquaspeedPotion);
    }

    private static void registerStencil(Item pattern, ArmorPart armorPart) {
        for(ArmorCore armorCore : ArmoryRegistry.getArmor()) {
            for(PartMaterialType partMaterialType : armorCore.getRequiredComponents()) {
                if(partMaterialType.getPossibleParts().contains(armorPart)) {
                    ItemStack stencil = new ItemStack(pattern);
                    Pattern.setTagForPart(stencil, armorPart);
                    TinkerRegistry.registerStencilTableCrafting(stencil);
                    return;
                }
            }
        }
    }
}
