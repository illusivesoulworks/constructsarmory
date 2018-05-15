/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.lib.utils;

import c4.conarm.ConstructsArmory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import slimeknights.tconstruct.library.Util;

import java.text.DecimalFormat;
import java.util.Locale;

public class ConstructUtils {

    public static final DecimalFormat dfPercentSpec = new DecimalFormat("#.#%");

    public static String getPrefixedName(String name) {
        if (!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Non-lowercase unlocalized name detected! %s", name));
        }
        return ConstructsArmory.MODID + "." + name;
    }

    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(ConstructsArmory.MODID, name);
    }

    public static <T extends Block> T registerItemBlock(IForgeRegistry<Item> registry, ItemBlock itemBlock) {
        itemBlock.setUnlocalizedName(itemBlock.getBlock().getUnlocalizedName());
        register(registry, itemBlock, itemBlock.getBlock().getRegistryName());
        return (T) itemBlock.getBlock();
    }

    public static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block, String name) {
        block.setUnlocalizedName(getPrefixedName(name));
        register(registry, block, name);
        return block;
    }

    public static <T extends Item> T registerItem(IForgeRegistry<Item> registry, T item, String name) {
        item.setUnlocalizedName(getPrefixedName(name));
        register(registry, item, name);
        return item;
    }

    private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, T toRegister, String name) {
        register(registry, toRegister, getResource(name));
    }

    private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, T toRegister, ResourceLocation name) {
        toRegister.setRegistryName(name);
        registry.register(toRegister);
    }
}
