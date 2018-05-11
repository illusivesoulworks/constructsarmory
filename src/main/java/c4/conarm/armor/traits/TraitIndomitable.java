package c4.conarm.armor.traits;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nonnull;
import java.util.List;

public class TraitIndomitable extends AbstractArmorTrait {

    private static final float MULTIPLIER = 0.2F;

    public TraitIndomitable() {
        super("indomitable", 0xffffff);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        if(!TinkerUtil.hasTrait(rootCompound, identifier)) {
            ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
            ArmorNBT original = ArmorTagUtil.getOriginalArmorStats(rootCompound);
            data.defense += original.defense * MULTIPLIER;
            data.toughness += original.toughness * MULTIPLIER;
            TagUtil.setToolTag(rootCompound, data.get());
        }
        super.applyEffect(rootCompound, modifierTag);
    }
}
