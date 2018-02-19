package c4.conarm.armor.traits;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.List;

public class TraitSkeletal extends AbstractArmorTrait {

    private static final float MULTIPLIER = 0.5F;

    public TraitSkeletal() {
        super("skeletal", TextFormatting.WHITE);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);

        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        ArmorNBT original = ArmorTagUtil.getOriginalArmorStats(rootCompound);
        data.toughness += original.toughness * MULTIPLIER;

        TagUtil.setToolTag(rootCompound, data.get());
    }

//    @Override
//    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
//        String loc = String.format(LOC_Extra, getModifierIdentifier());
//
//        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(MULTIPLIER)));
//    }
}
