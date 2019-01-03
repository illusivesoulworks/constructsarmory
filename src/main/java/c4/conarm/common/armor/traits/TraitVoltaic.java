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

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;

public class TraitVoltaic extends AbstractArmorTrait {

    public TraitVoltaic() {
        super("voltaic", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {

        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = 0;

            for (ItemStack armor : player.getArmorInventoryList()) {
                if (armor.getItem() instanceof ArmorCore) {
                    if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), identifier)) {
                        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
                        ChargeData data = modtag.getTagData(ChargeData.class);
                        if (data.charge >= 25F) {
                            level++;
                            discharge(armor, modtag, data);
                        }
                    }
                }
            }

            if (level > 0) {
                if(player instanceof EntityPlayerMP) {
                    Sounds.playSoundForAll(player, Sounds.shocking_discharge, 1f, 1f);
                }
                double radius = 2.0D * level;
                BlockPos pos = player.getPosition();
                List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), TraitUtils.IS_LIVING);
                for (Entity entity : entities) {
                    if (attackEntitySecondary(new EntityDamageSource("lightningBolt", player).setIsThornsDamage(), 2F * level, entity, false, true, false)) {
                        TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, entity, 5);
                    }
                }
            }
        }
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {

        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        ChargeData data = modtag.getTagData(ChargeData.class);

        if (source == DamageSource.LIGHTNING_BOLT) {
            addCharge(25F, armor, player, data);
            modtag.save();
            newDamage -= damage * 0.25F;
            return newDamage;
        }

        if(data.charge < 25F) {
            addCharge(damage * 0.25F, armor, player, data);
            modtag.save();
        }

        return newDamage;
    }

    private void addCharge(float change, ItemStack armor, Entity entity, ChargeData data) {
        data.charge += change;
        if(data.charge >= 25F) {
            TagUtil.setEnchantEffect(armor, true);
            if(entity instanceof EntityPlayerMP) {
                Sounds.PlaySoundForPlayer(entity, Sounds.shocking_charged, 0.8f, 0.8f + 0.2f * random.nextFloat());
            }
        }
    }

    private void discharge(ItemStack armor, ModifierTagHolder modtag, ChargeData data) {
        data.charge = 0;
        modtag.save();
        TagUtil.setEnchantEffect(armor, false);
    }

    public static class ChargeData extends ModifierNBT {

        float charge;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            charge = tag.getFloat("charge");
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setFloat("charge", charge);
        }
    }
}
