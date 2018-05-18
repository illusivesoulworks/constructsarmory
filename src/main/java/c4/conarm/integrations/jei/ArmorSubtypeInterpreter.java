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

package c4.conarm.integrations.jei;

import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.utils.TagUtil;

import javax.annotation.Nonnull;

public class ArmorSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {

    @Override
    @Nonnull
    public String apply(@Nonnull ItemStack stack) {
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getItemDamage());

        NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
        if (materials.getTagType() == TagUtil.TAG_TYPE_STRING) {
            builder.append(':');
            for (int i = 0; i < materials.tagCount(); i++) {
                if (i != 0) {
                    builder.append(',');
                }
                builder.append(materials.getStringTagAt(i));
            }
        }

        return builder.toString();
    }
}
