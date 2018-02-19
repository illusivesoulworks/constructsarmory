package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class TraitSteady extends AbstractArmorTrait {

    private static final float MODIFIER = 0.3F;

    public TraitSteady() {
        super("steady", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingKnockback(LivingKnockBackEvent evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            if (evt.getAttacker() instanceof  EntityLivingBase) {
                EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
                int level = ArmorHelper.getArmorAbilityLevel(player, this.identifier);
                if (level > 0) {
                    evt.setStrength(evt.getStrength() / level);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            if (evt.getSource().getImmediateSource() instanceof EntityLivingBase) {
                EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
                EntityLivingBase entity = (EntityLivingBase) evt.getSource().getImmediateSource();
                int level = ArmorHelper.getArmorAbilityLevel(player, this.identifier);
                if (level > 0) {
                    entity.knockBack(entity, level * 0.15F, (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(player.rotationYaw * 0.017453292F)));
                }
            }
        }
    }

//    @Override
//    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
//        if (player.isSneaking()) {
//            mods.addEffectiveness(MODIFIER);
//        }
//        return super.getModifications(player, mods, armor, source, damage, slot);
//    }
}
