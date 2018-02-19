package c4.conarm.armor.modifiers;

import c4.conarm.armor.ArmorTraits;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class ModFrostStep extends ToolModifier {

    public ModFrostStep() {
        super("frost_step", 0xccffff);

        addAspects(new ModifierAspect.LevelAspect(this, 2), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return !otherModifier.getIdentifier().equals(ArmorTraits.aquaspeed.getIdentifier());
    }

    @Override
    protected boolean canApplyCustom(ItemStack stack) throws TinkerGuiException {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET;
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        ModifierNBT data = ModifierNBT.readTag(modifierTag);
        int lvl = Math.min(data.level, Enchantments.FROST_WALKER.getMaxLevel());

        while (lvl > ToolBuilder.getEnchantmentLevel(rootCompound, Enchantments.FROST_WALKER)) {
            ToolBuilder.addEnchantment(rootCompound, Enchantments.FROST_WALKER);
        }
    }
}
