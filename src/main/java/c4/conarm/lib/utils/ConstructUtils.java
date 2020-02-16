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

package c4.conarm.lib.utils;

import c4.conarm.ConstructsArmory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

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
        itemBlock.setTranslationKey(itemBlock.getBlock().getTranslationKey());
        register(registry, itemBlock, itemBlock.getBlock().getRegistryName());
        return (T) itemBlock.getBlock();
    }

    public static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block, String name) {
        block.setTranslationKey(getPrefixedName(name));
        register(registry, block, name);
        return block;
    }

    public static <T extends Item> T registerItem(IForgeRegistry<Item> registry, T item, String name) {
        item.setTranslationKey(getPrefixedName(name));
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
