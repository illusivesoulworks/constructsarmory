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

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class TraitCombustible extends AbstractArmorTrait {

    public TraitCombustible() {
        super("combustible", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {

        if (!evt.getSource().isFireDamage() && !evt.getSource().isExplosion()) {
            return;
        }

        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = (int) ArmorHelper.getArmorAbilityLevel(player, this.identifier);
            if (level > 0) {
                double radius = 1.5D * level;
                BlockPos pos = player.getPosition();
                EntityDamageSource source = new EntityDamageSource("firewood", player);
                source.setIsThornsDamage();
                source.setFireDamage();
                List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), TraitUtils.IS_LIVING);
                for (Entity entity : entities) {
                    if (!entity.isImmuneToFire() && attackEntitySecondary(source, level, entity, true, false, false)) {
                        entity.setFire(1 + level);
                    }
                }
            }
        }
    }
}
