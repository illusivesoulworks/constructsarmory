package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.List;

import static slimeknights.tconstruct.tools.modifiers.ModReinforced.TAG_UNBREAKABLE;

public class ModReinforced extends ArmorModifierTrait {

    private static final float chancePerLevel = 0.20F;

    public ModReinforced() {
        super("reinforced", 0x502e83, 5, 0);
    }

    private float getReinforcedChance(NBTTagCompound modifierTag) {
        ModifierNBT data = ModifierNBT.readTag(modifierTag);

        return (float) data.level * chancePerLevel;
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);

        if(getReinforcedChance(modifierTag) >= 1F) {
            rootCompound.setBoolean(TAG_UNBREAKABLE, true);
        }
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        if(player.getEntityWorld().isRemote) {
            return 0;
        }

        NBTTagCompound tag = TinkerUtil.getModifierTag(armor, identifier);

        float chance = getReinforcedChance(tag);
        if(chance >= random.nextFloat()) {
            newDamage -= damage;
        }
        return Math.max(0, newDamage);
    }

    @Override
    public String getLocalizedDesc() {
        return String.format(super.getLocalizedDesc(), Util.dfPercent.format(chancePerLevel));
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        ModifierNBT data = ModifierNBT.readTag(modifierTag);
        if(data.level == maxLevel) {
            return Util.translate("modifier.%s.unbreakable", TinkerModifiers.modReinforced.getIdentifier());
        }
        return super.getTooltip(modifierTag, detailed);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getIdentifier());

        if(I18n.canTranslate(loc)) {
            float chance = getReinforcedChance(modifierTag);
            String chanceStr = Util.dfPercent.format(chance);
            if(chance >= 1f) {
                chanceStr = Util.translate("modifier.%s.unbreakable", TinkerModifiers.modReinforced.getIdentifier());
            }
            return ImmutableList.of(Util.translateFormatted(loc, chanceStr));
        }
        return super.getExtraInfo(tool, modifierTag);
    }
}
