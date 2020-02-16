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

package c4.conarm.lib.book.content;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.ArmoryRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.ItemStackList;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;

@SideOnly(Side.CLIENT)
public class ContentArmorModifierPolished extends ContentArmorModifier {

    public static final transient String ID = "armormodifier_polished";

    public ContentArmorModifierPolished() {
    }

    public ContentArmorModifierPolished(IModifier modifier) {
        super(modifier);
    }

    @Override
    protected ItemStackList getDemoArmor(ItemStack[][] inputItems) {
        if(inputItems.length == 0) {
            return ItemStackList.create();
        }

        ItemStackList demo = super.getDemoArmor(inputItems);

        ItemStackList out = ItemStackList.create();

        for(int i = 0; i < inputItems[0].length; i++) {
            if(inputItems[0][i].getItem() == ConstructsRegistry.polishingKit) {
                Material material = ConstructsRegistry.polishingKit.getMaterial(inputItems[0][i]);
                IModifier modifier = ArmoryRegistry.getArmorModifier("polished_armor" + material.getIdentifier());
                if(modifier != null) {
                    ItemStack stack = demo.get(i % demo.size()).copy();
                    modifier.apply(stack);
                    out.add(stack);
                }
            }
        }

        return out;
    }
}