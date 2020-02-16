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

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.common.armor.utils.ArmorHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

public class ModTravelNight extends AbstractTravelGoggles {

    public ModTravelNight() {
        super(VisionType.NIGHT_VISION);
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        AbstractTravelGoggles.GogglesData data = modtag.getTagData(AbstractTravelGoggles.GogglesData.class);
        if (!data.goggles) {
            ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 3, player, EntityEquipmentSlot.HEAD.getIndex());
        }
        super.onKeybinding(armor, player);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        AbstractTravelGoggles.GogglesData data = modtag.getTagData(AbstractTravelGoggles.GogglesData.class);
        if (data.goggles) {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, -44, false, false));
            if (!world.isRemote && player.ticksExisted % 100 == 0) {
                ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 1, player, EntityEquipmentSlot.HEAD.getIndex());
            }
        }
    }

    @Override
    public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {
        if (player.isPotionActive(MobEffects.NIGHT_VISION)) {
            PotionEffect effect = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
            if (effect != null && effect.getAmplifier() == -44) {
                player.removePotionEffect(MobEffects.NIGHT_VISION);
            }
        }
    }
}
