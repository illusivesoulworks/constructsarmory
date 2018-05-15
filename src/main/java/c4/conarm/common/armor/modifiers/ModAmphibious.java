/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;

public class ModAmphibious extends ArmorModifierTrait {

    public ModAmphibious() {
        super("amphibious", 0x00ccff);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (player.isInWater()) {
            if (!player.isPotionActive(MobEffects.WATER_BREATHING)) {
                player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20));
            }
        }
    }
}
