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

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModFrostWalker extends ArmorModifierTrait {

    public ModFrostWalker() {
        super("frostwalker", 0x009999, 2, 0);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {

        if (source == DamageSource.HOT_FLOOR) {
            ArmorHelper.damageArmor(armor, source, 3, player);
            evt.setCanceled(true);
        }

        return newDamage;
    }


    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (!world.isRemote)
        {
            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, this.identifier);
            PositionData data = modtag.getTagData(PositionData.class);
            if (data.pos.getX() != player.getPosition().getX() || data.pos.getZ() != player.getPosition().getZ()) {
                NBTTagCompound tag = TinkerUtil.getModifierTag(armor, getIdentifier());
                int level = ModifierNBT.readTag(tag).level;
                EnchantmentFrostWalker.freezeNearby(player, world, player.getPosition(), level);
                if (level > 1) {
                    magmatizeNearby(player, world, player.getPosition());
                }
                data.pos = player.getPosition();
                modtag.save();
            }
        }
    }

    public static void magmatizeNearby(EntityLivingBase entity, World worldIn, BlockPos pos) {

        float f = 2;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f)))
        {
            if (blockpos$mutableblockpos1.distanceSqToCenter(entity.posX, entity.posY, entity.posZ) <= (double)(f * f))
            {
                blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

                if (iblockstate.getMaterial() == Material.AIR)
                {
                    IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

                    if (iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == Blocks.LAVA || iblockstate1.getBlock() == Blocks.FLOWING_LAVA) && iblockstate1.getValue(BlockLiquid.LEVEL) == 0 && worldIn.mayPlace(ConstructsRegistry.softObsidian, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null))
                    {
                        IBlockState state = ConstructsRegistry.softObsidian.getDefaultState();
                        worldIn.setBlockState(blockpos$mutableblockpos1, state);
                        worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), ConstructsRegistry.softObsidian, MathHelper.getInt(entity.getRNG(), 60, 120));
                    }
                }
            }
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.FEET && super.canApplyCustom(stack);
    }

    public static class PositionData extends ModifierNBT {

        public BlockPos pos;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            pos = new BlockPos(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setInteger("x", pos.getX());
            tag.setInteger("y", pos.getY());
            tag.setInteger("z", pos.getZ());
        }
    }
}
