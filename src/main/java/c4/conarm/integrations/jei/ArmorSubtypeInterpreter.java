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
