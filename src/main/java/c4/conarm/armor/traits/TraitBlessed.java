package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;

import java.util.List;

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

//    @Override
//    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
//        if (source.getTrueSource() instanceof EntityLivingBase) {
//            if (((EntityLivingBase) source.getTrueSource()).isEntityUndead()) {
//                mods.addEffectiveness(MODIFIER);
//            }
//        }
//        return super.getModifications(player, mods, armor, source, damage, slot);
//    }
//
//    @Override
//    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
//        String loc = Util.translate(LOC_Extra, getIdentifier());
//        return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(MODIFIER)));
//    }
}
