/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import java.util.List;

public class ModAmphibious extends ArmorModifierTrait {

    private static final String TAG_OXYGEN = "oxygen";
    private static final int MAX_CAPACITY = 1200;

    public ModAmphibious() {
        super("amphibious", 0x00ccff);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (!world.isRemote) {
            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
            OxygenData data = modtag.getTagData(OxygenData.class);

            if (player.isInWater()) {
                if (player.getAir() < 300 && data.oxygen > 0) {
                    addOxygen(modtag, data, -1);
                    player.setAir(player.getAir() + 1);
                }
            } else if (canStoreOxygen(data)) {
                addOxygen(modtag, data, MAX_CAPACITY);
            }
        }
    }

    private boolean canStoreOxygen(OxygenData data) {
        return data.oxygen < MAX_CAPACITY;
    }

    private void addOxygen(ModifierTagHolder modtag, OxygenData data, int amount) {
        data.oxygen = MathHelper.clamp(data.oxygen + amount, 0, MAX_CAPACITY);
        modtag.save();
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && super.canApplyCustom(stack);
    }

    public static class OxygenData extends ModifierNBT {

        public int oxygen = 0;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            oxygen = tag.getInteger(TAG_OXYGEN);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setInteger(TAG_OXYGEN, oxygen);
        }
    }
}
