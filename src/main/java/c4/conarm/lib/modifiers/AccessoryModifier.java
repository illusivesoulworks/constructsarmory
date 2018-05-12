package c4.conarm.lib.modifiers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

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
