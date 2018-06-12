/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IToggleable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public abstract class AbstractToggleAccessoryModifier extends AccessoryModifier implements IToggleable {

    private static final String TAG_TOGGLE = "toggle";

    protected boolean damagesArmor;

    public AbstractToggleAccessoryModifier(String identifier, boolean damagesArmor) {
        super(identifier);
        this.damagesArmor = damagesArmor;
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (!world.isRemote && damagesArmor && player.ticksExisted % 100 == 0 && getToggleData(armor).toggle) {
            ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 1, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        }
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        toggle(armor, player);
    }

    private void toggle(ItemStack stack, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        ToggleData data = modtag.getTagData(ToggleData.class);
        if (!data.toggle && damagesArmor) {
            ArmorHelper.damageArmor(stack, DamageSource.GENERIC, 3, player, EntityLiving.getSlotForItemStack(stack).getIndex());
        }
        data.toggle = !data.toggle;
        modtag.save();
    }

    protected ToggleData getToggleData(ItemStack stack) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(stack, getModifierIdentifier());
        return modtag.getTagData(ToggleData.class);
    }

    @Override
    public boolean getToggleStatus(ItemStack stack) {
        return getToggleData(stack).toggle;
    }

    public static class ToggleData extends ModifierNBT {

        public boolean toggle = false;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            toggle = tag.getBoolean(TAG_TOGGLE);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setBoolean(TAG_TOGGLE, toggle);
        }
    }
}
