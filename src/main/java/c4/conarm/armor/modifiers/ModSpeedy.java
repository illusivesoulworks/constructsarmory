package c4.conarm.armor.modifiers;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.List;
import java.util.Random;

public class ModSpeedy extends ArmorModifierTrait {

    public ModSpeedy() {
        super("speedy", 0x910000, 1, 50);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        //Check to see if the player is moving/running (Y-motion doesn't matter here)
//        if (player.motionX != 0 || player.motionZ != 0) {
//            this.spawnParticles(world, player.getPosition());
//        }
    }

    private void spawnParticles(World worldIn, BlockPos pos)
    {
        Random random = worldIn.rand;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i)
        {
            double d1 = (double)((float)pos.getX() + random.nextFloat());
            double d2 = (double)((float)pos.getY() + random.nextFloat());
            double d3 = (double)((float)pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
            {
                d2 = (double)pos.getY() + d0 + 1.0D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
            {
                d2 = (double)pos.getY() - d0;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() + d0 + 1.0D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
            {
                d3 = (double)pos.getZ() - d0;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
            {
                d1 = (double)pos.getX() + d0 + 1.0D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
            {
                d1 = (double)pos.getX() - d0;
            }

            if (d1 < (double)pos.getX() || d1 > (double)(pos.getX() + 1) || d2 < 0.0D || d2 > (double)(pos.getY() + 1) || d3 < (double)pos.getZ() || d3 > (double)(pos.getZ() + 1))
            {
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

        super.applyEffect(rootCompound, modifierTag);

        ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);
        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);

//        data.movementSpeedMultiplier += getSpeedBonus(modData);

        TagUtil.setToolTag(rootCompound, data.get());
    }

    protected float getSpeedBonus(ModifierNBT.IntegerNBT modData) {
        return 0.2f * modData.current / modData.max;
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return getLeveledTooltip(modifierTag, detailed);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getIdentifier());

        ImmutableList.Builder<String> builder = ImmutableList.builder();

        float bonus = getSpeedBonus(ModifierNBT.readInteger(modifierTag));
        builder.add(Util.translateFormatted(loc, Util.dfPercent.format(bonus)));
        return builder.build();
    }
}
