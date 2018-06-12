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

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TraitSteady extends AbstractArmorTrait {

    protected static final UUID[] KNOCKBACK_MODIFIERS = new UUID[]{ UUID.fromString("e2b8e0eb-7f25-4d61-aa05-e944b0ef405e"),
            UUID.fromString("5386d00f-8016-46c7-85fd-d3327e6b28fd"),
            UUID.fromString("d1d85377-7d7f-4918-be58-6ab27f41be3d"),
            UUID.fromString("7cb4787d-9060-4274-ba90-03227daae664") };
    private static final double KNOCKBACK_RESIST_PER_LEVEL = 0.1D;

    public TraitSteady() {
        super("steady", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(KNOCKBACK_MODIFIERS[slot.getIndex()], "Steady trait modifier", KNOCKBACK_RESIST_PER_LEVEL, 0));
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            if (evt.getSource().getImmediateSource() instanceof EntityLivingBase) {
                EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
                EntityLivingBase entity = (EntityLivingBase) evt.getSource().getImmediateSource();
                float knockbackResist = (float) entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
                if (knockbackResist > 0) {
                    entity.knockBack(entity, knockbackResist * 2, (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(player.rotationYaw * 0.017453292F)));
                }
            }
        }
    }
}
