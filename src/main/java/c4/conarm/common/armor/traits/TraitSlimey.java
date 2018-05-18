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
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import slimeknights.tconstruct.library.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TraitSlimey extends AbstractArmorTrait {

    private static final float CHANCE = 0.01F;
    private static final Method SET_SLIME_SIZE = ReflectionHelper.findMethod(EntitySlime.class, "setSlimeSize", "func_70799_a", Integer.TYPE, Boolean.TYPE);

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
            SET_SLIME_SIZE.invoke(entity, 1, true);
            entity.setPosition(x, y, z);
            world.spawnEntity(entity);
            entity.setLastAttackedEntity(player);
            entity.playLivingSound();
        } catch(InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
