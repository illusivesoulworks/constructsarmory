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

package c4.conarm.lib.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;

public abstract class AccessoryModifier extends ArmorModifierTrait {

    public AccessoryModifier(String identifier) {
        super(identifier, 0xf4db42);
        addAspects(new ModifierAspect.SingleAspect(this));

    }

    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        //NO-OP
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        //Can only have one accessory per item
        NBTTagList list = TagUtil.getTraitsTagList(stack);
        for (int i = 0; i < list.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
            if (trait != null && trait instanceof AccessoryModifier) {
                return false;
            }
        }

        return super.canApplyCustom(stack);
    }
}
