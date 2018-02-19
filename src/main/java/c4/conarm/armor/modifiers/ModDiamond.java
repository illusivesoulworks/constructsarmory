package c4.conarm.armor.modifiers;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModDiamond extends ToolModifier {

    public ModDiamond() {
        super("diamond_armor", 0x8cf4e2);

        addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        data.durability += 500;

        data.armor += 1.0F;
        data.toughness += 0.5F;

        TagUtil.setToolTag(rootCompound, data.get());
    }
}
