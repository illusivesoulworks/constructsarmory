package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.List;
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
