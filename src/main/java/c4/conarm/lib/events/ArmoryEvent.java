/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.lib.events;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolCore;

public abstract class ArmoryEvent extends Event {

    /**
     * Fired when an armor piece is built.
     * This happens every time an armor piece is loaded as well as when the player actually builds the armor piece.
     * You can make changes to the tag compound and it'll land on the resulting armor, but its itemstack is not available.
     */
    public static class OnItemBuilding extends ArmoryEvent {

        public NBTTagCompound tag;
        public final ImmutableList<Material> materials;
        public final TinkersArmor armor;

        public OnItemBuilding(NBTTagCompound tag, ImmutableList<Material> materials, TinkersArmor armor) {
            this.tag = tag;
            this.materials = materials;
            this.armor = armor;
        }

        public static OnItemBuilding fireEvent(NBTTagCompound tag, ImmutableList<Material> materials, TinkersArmor armor) {
            OnItemBuilding event = new OnItemBuilding(tag, materials, armor);
            MinecraftForge.EVENT_BUS.post(event);
            return event;
        }
    }

    public static class OnRepair extends ArmoryEvent {

        public final int amount;
        public final ItemStack itemStack;
        public final ArmorCore armor;

        public OnRepair(ItemStack itemStack, int amount) {
            this.itemStack = itemStack;
            this.armor = (ArmorCore) itemStack.getItem();
            this.amount = amount;
        }

        public static boolean fireEvent(ItemStack itemStack, int amount) {
            OnRepair event = new OnRepair(itemStack, amount);
            return !MinecraftForge.EVENT_BUS.post(event);
        }
    }
}