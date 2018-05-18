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

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TraitBlessed extends AbstractArmorTrait {

    private static final float MODIFIER = 0.2F;

    public TraitBlessed() {
        super("blessed", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {

        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            if (evt.getSource().getImmediateSource() instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) evt.getSource().getImmediateSource();
                if (entity.isEntityUndead()) {
                    int level = (int) ArmorHelper.getArmorAbilityLevel(player, getModifierIdentifier());
                    if (level > 0) {
                        entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100 * level, level - 1));
                    }
                }
            }
        }
    }
}
