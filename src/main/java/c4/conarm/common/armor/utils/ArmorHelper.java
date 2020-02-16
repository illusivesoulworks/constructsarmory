/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.utils;

import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.tinkering.TinkersArmor;
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
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;

import static slimeknights.tconstruct.library.utils.ToolHelper.getCurrentDurability;
import static slimeknights.tconstruct.library.utils.ToolHelper.unbreakTool;

public class ArmorHelper {

    public static final int[] durabilityMultipliers = new int[]{13, 15, 16, 11};
    public static final float[] defenseMultipliers = new float[]{0.14F, 0.3F, 0.4F, 0.16F};
    protected static final float[] ARMOR_PROTECTION_CAPS = {0.12F, 0.24F, 0.32F, 0.12F};

    public static boolean isUnbrokenTinkersArmor(ItemStack stack) {
        return stack.getItem() instanceof TinkersArmor && !ToolHelper.isBroken(stack);
    }

    public static boolean disableRender(ItemStack stack, EntityLivingBase entityLivingBase) {
        if (stack.getItem() instanceof TinkersArmor) {
            NBTTagList list = TagUtil.getTraitsTagList(stack);
            for (int i = 0; i < list.tagCount(); i++) {
                ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                if (trait != null && trait instanceof IArmorTrait) {
                    if (((IArmorTrait) trait).disableRendering(stack, entityLivingBase)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ISpecialArmor.ArmorProperties getPropertiesAfterAbsorb(ItemStack armor, double damage, float totalArmor, float totalToughness, EntityEquipmentSlot slot) {

        float defenseReduction = (float) damage / (totalToughness / 4 + 2) * 0.01F;
        float absorbRatio = MathHelper.clamp(totalArmor / 25F - defenseReduction, totalArmor / 125F, ARMOR_PROTECTION_CAPS[slot.getIndex()]);

        return new ISpecialArmor.ArmorProperties(0, absorbRatio, (int) Math.ceil(Math.max(damage, (armor.getMaxDamage() - armor.getItemDamage()) * 4)));
    }

    public static float getArmor(ItemStack stack, int slot) {
        if (slot > 3) {
            return 0;
        }
        return ((int) (getDefense(stack) * defenseMultipliers[slot] * 100 + 0.5)) / 100.0F;
    }

    public static float getDefense(ItemStack stack) {
        return getFloatTag(stack, ArmorTagUtil.DEFENSE);
    }

    public static float getToughness(ItemStack stack) {
        return getFloatTag(stack, ArmorTagUtil.TOUGHNESS);
    }

    private static float getFloatTag(ItemStack stack, String key) {
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

    public static void damageArmor(ItemStack stack, DamageSource source, int amount, EntityPlayer player) {
        damageArmor(stack, source, amount, player, EntityLiving.getSlotForItemStack(stack).getIndex());
    }

    //Use the slot-less version above instead
    @Deprecated
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
                NBTTagCompound compound = list.getCompoundTagAt(i);
                IModifier mod = TinkerRegistry.getModifier(compound.getString("identifier"));
                if (mod != null && mod instanceof IArmorAbility) {
                    ModifierNBT data = ModifierNBT.readTag(compound);
                    ArmorHelper.removeArmorAbility(player, mod.getIdentifier(), ((IArmorAbility) mod).getAbilityLevel(data));
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
