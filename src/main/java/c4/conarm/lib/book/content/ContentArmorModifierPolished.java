/*
 * Copyright (c) 2018-2019 <C4>
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