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

import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.List;

public class TraitMagnetic extends AbstractArmorTraitLeveled {

    public TraitMagnetic(int lvl) {
        super("magnetic", 0xdddddd, 3, lvl);
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {

        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        double range = 1.8D + (level - 1) * 0.3F;

        List<EntityItem> items = player.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
        int pulled = 0;
        for(EntityItem item : items) {
            if(item.getItem().isEmpty() || item.isDead || item.cannotPickup()) {
                continue;
            }

            if(pulled > 200) {
                break;
            }

            float strength = 0.07f;

            Vector3d vec = new Vector3d(x, y, z);
            vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

            vec.normalize();
            vec.scale(strength);

            item.motionX += vec.x;
            item.motionY += vec.y;
            item.motionZ += vec.z;

            pulled++;
        }
    }
}
