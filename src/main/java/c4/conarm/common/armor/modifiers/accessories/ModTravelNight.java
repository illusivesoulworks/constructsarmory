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
    public void onArmorChanged(ItemStack armor, EntityPlayer player, int slot) {
        if (player.isPotionActive(MobEffects.NIGHT_VISION)) {
            PotionEffect effect = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
            if (effect != null && effect.getAmplifier() == -44) {
                player.removePotionEffect(MobEffects.NIGHT_VISION);
            }
        }
    }
}
