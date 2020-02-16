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

import c4.conarm.ConstructsArmory;
import c4.conarm.common.armor.traits.TraitUtils;
import c4.conarm.common.armor.utils.ArmorHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import java.util.List;

public class ModTravelSoul extends AbstractTravelGoggles {

    public ModTravelSoul() {
        super(VisionType.SOUL);
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        if (!data.goggles) {
            ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 3, player, EntityEquipmentSlot.HEAD.getIndex());
        }
        super.onKeybinding(armor, player);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        if (data.goggles) {
            int radius = 20;
            BlockPos pos = player.getPosition();
            List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), TraitUtils.IS_LIVING);
            for (Entity entity : entities) {
                ConstructsArmory.proxy.generateParticle(entity);
            }
            if (!world.isRemote && player.ticksExisted % 100 == 0) {
                ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 1, player, EntityEquipmentSlot.HEAD.getIndex());
            }
        }
    }
}
