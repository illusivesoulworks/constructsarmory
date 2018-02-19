package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class TraitEnderport extends AbstractArmorTrait {

    private static final float MODIFIER = 0.25F;

    public TraitEnderport() {
        super("enderport", TextFormatting.DARK_AQUA);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer && isStuck(evt.getSource())) {
            int level = ArmorHelper.getArmorAbilityLevel((EntityPlayer) evt.getEntityLiving(), getModifierIdentifier());
            if (level > 0 && random.nextFloat() < MODIFIER * level) {
                for (int i = 0; i < 64; i++) {
                    if (teleportRandomly(evt.getEntityLiving())) {
                        return;
                    }
                }
            }
        }
    }

    protected boolean isStuck(DamageSource source) {
        return source == DamageSource.LAVA || source == DamageSource.DROWN || source == DamageSource.IN_WALL;
    }

    protected boolean teleportRandomly(EntityLivingBase entity)
    {
        double x = entity.posX + (random.nextDouble() - 0.5D) * 64.0D;
        double y = entity.posY + (double)(random.nextInt(64) - 32);
        double z = entity.posZ + (random.nextDouble() - 0.5D) * 64.0D;
        EnderTeleportEvent evt = new EnderTeleportEvent(entity, x, y, z, 0);
        if (MinecraftForge.EVENT_BUS.post(evt)) return false;
        boolean flag = attemptTeleport(entity, evt.getTargetX(), evt.getTargetY(), evt.getTargetZ());
        if (flag) {
            entity.world.playSound(null, entity.prevPosX, entity.prevPosY, entity.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
            entity.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }
        return flag;
    }

    //A version of the vanilla Minecraft teleport method that also allows for positions at a body of water's surface
    private boolean attemptTeleport(EntityLivingBase entity, double x, double y, double z)
    {
        double d0 = entity.posX;
        double d1 = entity.posY;
        double d2 = entity.posZ;
        entity.posX = x;
        entity.posY = y;
        entity.posZ = z;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(entity);
        World world = entity.world;
        Random random = entity.getRNG();

        if (world.isBlockLoaded(blockpos))
        {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0)
            {
                BlockPos blockpos1 = blockpos.down();
                IBlockState iblockstate = world.getBlockState(blockpos1);

                if (iblockstate.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    entity.posY--;
                    blockpos = blockpos1;
                }
            }

            if (flag1)
            {
                entity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);

                if (world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entity.getEntityBoundingBox()))
                {
                    flag = !world.containsAnyLiquid(entity.getEntityBoundingBox()) || (entity.isInsideOfMaterial(Material.WATER) && world.isAirBlock(entity.getPosition().up()));
                }
            }
        }

        if (!flag)
        {
            entity.setPositionAndUpdate(d0, d1, d2);
            return false;
        }
        else
        {
            int i = 128;

            for (int j = 0; j < 128; ++j)
            {
                double d6 = (double)j / 127.0D;
                float f = (random.nextFloat() - 0.5F) * 0.2F;
                float f1 = (random.nextFloat() - 0.5F) * 0.2F;
                float f2 = (random.nextFloat() - 0.5F) * 0.2F;
                double d3 = d0 + (entity.posX - d0) * d6 + (random.nextDouble() - 0.5D) * entity.width * 2.0D;
                double d4 = d1 + (entity.posY - d1) * d6 + random.nextDouble() * entity.height;
                double d5 = d2 + (entity.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * entity.width * 2.0D;
                world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2);
            }

            if (entity instanceof EntityCreature)
            {
                ((EntityCreature)entity).getNavigator().clearPath();
            }

            return true;
        }
    }
}
