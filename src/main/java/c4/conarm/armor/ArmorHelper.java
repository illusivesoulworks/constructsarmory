package c4.conarm.armor;

import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.traits.IArmorAbility;
import c4.conarm.lib.traits.IArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ISpecialArmor;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;

import static slimeknights.tconstruct.library.utils.ToolHelper.*;

public class ArmorHelper {

    public static final int[] durabilityMultipliers = new int[]{13, 15, 16, 11};
    protected static final float[] ARMOR_PROTECTION_CAPS = {0.12F, 0.24F, 0.32F, 0.12F};

    public static ISpecialArmor.ArmorProperties getPropertiesAfterAbsorb(ItemStack armor, double damage, float totalArmor, float totalToughness, EntityEquipmentSlot slot) {

        float defenseReduction = (float) damage * 4 / (totalToughness / 4 + 2) * 0.01F;
        float absorbRatio = MathHelper.clamp(totalArmor / 25F - defenseReduction, totalArmor / 125F, ARMOR_PROTECTION_CAPS[slot.getIndex()]);

        return new ISpecialArmor.ArmorProperties(0, absorbRatio, (int) Math.ceil(Math.max(damage, (armor.getMaxDamage() - armor.getItemDamage()) * 4)));
    }

    public static float getActualArmor(ItemStack stack) {
        return getArmorStat(stack);
    }

    public static float getActualToughness(ItemStack stack) {
        return getToughnessStat(stack);
    }

    public static float getArmorStat(ItemStack stack) {
        return getFloatTag(stack, ArmorTagUtil.ARMOR);
    }

    public static float getToughnessStat(ItemStack stack) {
        return getFloatTag(stack, ArmorTagUtil.TOUGHNESS);
    }

    static float getFloatTag(ItemStack stack, String key) {
        NBTTagCompound tag = TagUtil.getToolTag(stack);

        return tag.getFloat(key);
    }

    public static double getArmorAbilityLevel(EntityPlayer entityplayer, String identifier) {
        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(entityplayer);
        if (armorAbilities != null) {
            return armorAbilities.getAbilityLevel(identifier);
        }
        return 0;
    }

    public static void addArmorAbility(EntityPlayer entityplayer, String identifier, int amount) {
        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(entityplayer);
        if (armorAbilities != null) {
            armorAbilities.addAbility(identifier, amount);
        }
    }

    public static void removeArmorAbility(EntityPlayer entityplayer, String identifier, int amount) {
        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(entityplayer);
        if (armorAbilities != null) {
            armorAbilities.removeAbility(identifier, amount);
        }
    }

    public static void damageArmor(ItemStack stack, DamageSource source, int amount, EntityPlayer player, int slot) {
        if(amount == 0 || ToolHelper.isBroken(stack)) {
            return;
        }

        int actualAmount = amount;
        NBTTagList list = TagUtil.getTraitsTagList(stack);
        for(int i = 0; i < list.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
            if(trait != null && trait instanceof IArmorTrait) {
                if(amount > 0) {
                    actualAmount = ((IArmorTrait) trait).onArmorDamage(stack, source, amount, actualAmount, player, slot);
                }
                else {
                    actualAmount = ((IArmorTrait) trait).onArmorHeal(stack, source, amount, actualAmount, player, slot);
                }
            }
        }

        if(actualAmount > 0 && TagUtil.getTagSafe(stack).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            actualAmount = 0;
        }

        actualAmount = Math.min(actualAmount, getCurrentDurability(stack));
        stack.setItemDamage(stack.getItemDamage() + actualAmount);

        if(ToolHelper.getCurrentDurability(stack) == 0) {
            ToolHelper.breakTool(stack, player);
            for (int i = 0; i < list.tagCount(); i++) {
                ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                if (trait != null && trait instanceof IArmorAbility) {
                    NBTTagCompound modifierTag = new NBTTagCompound();
                    NBTTagList tagList = TagUtil.getModifiersTagList(TagUtil.getTagSafe(stack));
                    int index = TinkerUtil.getIndexInList(tagList, trait.getIdentifier());
                    if(index >= 0) {
                        modifierTag = tagList.getCompoundTagAt(index);
                    }
                    ArmorHelper.removeArmorAbility(player, trait.getIdentifier(), ((IArmorAbility) trait).getAbilityLevel(modifierTag));
                }
            }
        }
    }

    public static void healArmor(ItemStack stack, int amount, EntityPlayer player, int slot) {
        damageArmor(stack, new DamageSource("heal"), -amount, player, slot);
    }

    public static void repairArmor(ItemStack stack, int amount) {
        repairArmor(stack, amount, null);
    }

    public static void repairArmor(ItemStack stack, int amount, EntityPlayer player) {
        unbreakTool(stack);

        ArmoryEvent.OnRepair.fireEvent(stack, amount);

        healArmor(stack, amount, player, EntityLiving.getSlotForItemStack(stack).getIndex());
    }
}
