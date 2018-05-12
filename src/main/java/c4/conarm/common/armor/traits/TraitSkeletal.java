package c4.conarm.common.armor.traits;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.utils.TagUtil;

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
}
