package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ModSpeedy extends ArmorModifierTrait {

    protected static final UUID[] SPEED_MODIFIERS = new UUID[]{
            UUID.fromString("857af40b-def8-47e3-a838-527933eca586"),
            UUID.fromString("1fd2c8fb-5a76-4e6f-bccd-c27f2287ad1b"),
            UUID.fromString("54e47051-19ac-4e59-9e54-4f256484408a"),
            UUID.fromString("ac16532f-d0b0-4b23-a89a-85ecaa3b5d7d") };

    public ModSpeedy() {
        super("speedy", 0x910000, 1, 20);
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
    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if(slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(SPEED_MODIFIERS[slot.getIndex()], "Speedy modifier", getSpeedBonus(stack), 2));
        }
    }

    protected float getSpeedBonus(ItemStack stack) {
        NBTTagCompound modifierTag = new NBTTagCompound();
        NBTTagList tagList = TagUtil.getModifiersTagList(TagUtil.getTagSafe(stack));
        int index = TinkerUtil.getIndexInList(tagList, identifier);
        if(index >= 0) {
            modifierTag = tagList.getCompoundTagAt(index);
        }
        ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);
        return getSpeedBonus(modData);
    }

    protected float getSpeedBonus(ModifierNBT.IntegerNBT modData) {
        return 0.1F * modData.current / modData.max;
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
