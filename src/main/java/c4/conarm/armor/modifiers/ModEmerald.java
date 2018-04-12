package c4.conarm.armor.modifiers;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.modifiers.ArmorModifier;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModEmerald extends ArmorModifier {

    public ModEmerald() {
        super("emerald", 0x41f384);

        addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        ArmorNBT base = ArmorTagUtil.getOriginalArmorStats(rootCompound);

        data.durability += base.durability / 2;
        data.toughness = Math.max(2, data.toughness);

        TagUtil.setToolTag(rootCompound, data.get());
    }
}
