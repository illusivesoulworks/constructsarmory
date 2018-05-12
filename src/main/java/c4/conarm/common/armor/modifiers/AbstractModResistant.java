package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;

public abstract class AbstractModResistant extends ArmorModifierTrait {

    protected static final int baseCount = 1;
    protected static final int maxLevel = 4;

    private final Enchantment enchantment;
    private final AbstractModResistant.ResistanceAspect aspect;

    public AbstractModResistant(String identifier, int color, Enchantment enchantment) {
        super(identifier, color, maxLevel, 0);

        this.enchantment = enchantment;
        aspects.clear();
        aspect = new AbstractModResistant.ResistanceAspect(this);
        addAspects(aspect);
    }

    public int getResistanceLevel(ItemStack itemStack) {
        return getResistanceLevel(TinkerUtil.getModifierTag(itemStack, getModifierIdentifier()));
    }

    public int getResistanceLevel(NBTTagCompound modifierTag) {
        ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);
        return aspect.getLevel(data.current);
    }

    @Override
    public abstract boolean canApplyTogether(Enchantment enchantment);

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        int lvl = getResistanceLevel(modifierTag);

        applyEnchantments(rootCompound, lvl);
    }

    protected void applyEnchantments(NBTTagCompound rootCompound, int lvl) {

        lvl = Math.min(lvl, enchantment.getMaxLevel());

        while(lvl > ToolBuilder.getEnchantmentLevel(rootCompound, enchantment)) {
            ToolBuilder.addEnchantment(rootCompound, enchantment);
        }
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        int level = getResistanceLevel(modifierTag);

        String tooltip = getLocalizedName();
        if(level > 0) {
            tooltip += " " + TinkerUtil.getRomanNumeral(level);
        }

        if(detailed) {
            ModifierNBT data = ModifierNBT.readInteger(modifierTag);
            tooltip += " " + data.extraInfo;
        }
        return tooltip;
    }

    public static class ResistanceAspect extends ModifierAspect.MultiAspect {

        public ResistanceAspect(IModifier parent) {
            super(parent, 0x5a82e2, maxLevel, baseCount, 1);

            freeModifierAspect = new FreeFirstModifierAspect(parent, 1);
        }

        @Override
        protected int getMaxForLevel(int level) {
            return (countPerLevel * level * (level + 1)) / 2; // sum(n)
        }

        public int getLevel(int current) {
            int i = 0;
            while(current >= getMaxForLevel(i + 1)) {
                i++;
            }
            return i;
        }
    }
}
