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
