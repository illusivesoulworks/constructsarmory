/*
 * Copyright (c) 2018-2019 <C4>
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
