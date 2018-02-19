package c4.conarm.armor.traits;

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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class TraitLightweight extends AbstractArmorTrait {

    protected static final UUID[] SPEED_MODIFIERS = new UUID[]{ UUID.fromString("6b1a5e14-1589-4273-86c4-cc0df937c108"),
                                                                UUID.fromString("69c21f84-449d-4e18-89c1-b9f7b5ca98f0"),
                                                                UUID.fromString("37f79f7c-5cca-426b-824b-9ba2ced92337"),
                                                                UUID.fromString("1316663f-febe-430f-8714-94bf21118ab6") };
    private static final double SPEED_PER_LEVEL = 0.1D;

    public TraitLightweight() {
        super("lightweight_armor", 0x00ff00);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(SPEED_MODIFIERS[slot.getIndex()], "Lightweight trait modifier", SPEED_PER_LEVEL, 2));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());

        return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(SPEED_PER_LEVEL)));
    }
}
