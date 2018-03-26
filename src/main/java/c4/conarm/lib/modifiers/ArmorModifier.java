package c4.conarm.lib.modifiers;

import c4.conarm.lib.tinkering.TinkersArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public abstract class ArmorModifier extends ToolModifier {

    public ArmorModifier(String identifier, int color) {
        super(identifier + "_armor", color);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {

        return stack.getItem() instanceof TinkersArmor;
    }
}
