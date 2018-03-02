package c4.conarm.proxy;

import c4.conarm.armor.common.RepairRecipe;
import c4.conarm.armor.traits.TraitAquaspeed;
import c4.conarm.client.GuiHandler;
import c4.conarm.common.PlayerDataEvents;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.ConstructsArmory;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.armor.ArmorEvents;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorPart;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.materials.ArmorMaterials;
import c4.conarm.armor.ArmorModifiers;
import c4.conarm.armor.common.network.ConstructsNetwork;
import c4.conarm.armor.traits.TraitSuperhot;
import c4.conarm.armor.common.blocks.BlockArmorForge;
import c4.conarm.armor.common.blocks.BlockSoftMagma;
import c4.conarm.armor.common.tileentities.TileArmorForge;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagList;
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
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.TableRecipeFactory;
import slimeknights.tconstruct.tools.common.block.BlockToolTable;
import slimeknights.tconstruct.tools.common.item.ItemBlockTable;

import java.util.List;
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
        ArmorModifiers.setupModifiers();
        MinecraftForge.EVENT_BUS.register(new ArmorEvents());
        MinecraftForge.EVENT_BUS.register(new ArmorAbilityHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerDataEvents());
        CapabilityManager.INSTANCE.register(ArmorAbilityHandler.IArmorAbilities.class, new ArmorAbilityHandler.Storage() , ArmorAbilityHandler.ArmorAbilities::new);
        NetworkRegistry.INSTANCE.registerGuiHandler(ConstructsArmory.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent evt) {
//        ArmorModifiers.registerExtraTraitModifiers();
        ArmorModifiers.registerPolishedModifiers();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        IForgeRegistry<Block> registry = evt.getRegistry();

        ConstructsArmor.armorForge = ConstructUtils.registerBlock(registry, new BlockArmorForge(), "armorforge");
//        ConstructsArmor.softMagma = ConstructUtils.registerBlock(registry, new BlockSoftMagma(), "soft_magma");

        GameRegistry.registerTileEntity(TileArmorForge.class, "armorforge");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> registry = evt.getRegistry();

        ConstructsArmor.registerArmorParts(registry);
        ConstructsArmor.registerArmorPieces(registry);
        ConstructsArmor.registerItems(registry);
        ConstructsArmor.armorForge = ConstructUtils.registerItemBlock(registry, new ItemBlockTable(ConstructsArmor.armorForge));

        for(Pair<Item, ArmorPart> armorPartPattern : ConstructsArmor.armorPartPatterns) {
            registerStencil(armorPartPattern.getLeft(), armorPartPattern.getRight());
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();

        if (ConstructsArmor.armorForge != null) {
            ConstructsArmor.armorForge.baseBlocks.addAll(TinkerTools.toolForge.baseBlocks);
            for (String oredict : ConstructsArmor.armorForge.baseBlocks) {
                Block brick = TinkerSmeltery.searedBlock;
                if(brick == null) {
                    brick = Blocks.STONEBRICK;
                }

                TableRecipeFactory.TableRecipe recipe = new TableRecipeFactory.TableRecipe(new ResourceLocation(ConstructsArmory.MODID, "armorforge"), new OreIngredient(oredict), new ItemStack(ConstructsArmor.armorForge), CraftingHelper.parseShaped(new Object[]{"BBB", "MTM", "MMM", Character.valueOf('B'), brick, Character.valueOf('M'), oredict, Character.valueOf('T'), new ItemStack(TinkerTools.toolTables, 1, BlockToolTable.TableTypes.ToolStation.meta)}));
                recipe.setRegistryName("armorforge_" + oredict.toLowerCase(Locale.US));
                registry.register(recipe);
            }
        }

        registry.register(new RepairRecipe());
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt) {
        evt.getRegistry().register(TraitSuperhot.superhotPotion);
        evt.getRegistry().register(TraitAquaspeed.aquaspeedPotion);
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
