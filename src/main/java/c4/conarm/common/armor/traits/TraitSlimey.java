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

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.library.Util;

import java.lang.reflect.InvocationTargetException;

public class TraitSlimey extends AbstractArmorTrait {

    private static final float CHANCE = 0.01F;

    protected final Class<? extends EntitySlime> slime;

    public TraitSlimey(String suffix, Class<? extends EntitySlime> slime) {
        super("slimey_" + suffix, TextFormatting.GREEN);
        this.slime = slime;
    }

    @Override
    public String getLocalizedName() {
        return Util.translate(String.format(LOC_Name, "slimey"));
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(String.format(LOC_Desc, "slimey"));
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {

        EntityLivingBase entity = evt.getEntityLiving();

        if(!entity.world.isRemote && random.nextFloat() < CHANCE) {
            spawnSlime(entity, entity.posX + 0.5, entity.posY, entity.posZ + 0.5, entity.world);
        }

        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    protected void spawnSlime(EntityLivingBase player, double x, double y, double z, World world) {
        try {
            EntitySlime entity = slime.getConstructor(World.class).newInstance(world);
            entity.setSlimeSize(1, true);
            entity.setPosition(x, y, z);
            world.spawnEntity(entity);
            entity.setLastAttackedEntity(player);
            entity.playLivingSound();
        } catch(InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
