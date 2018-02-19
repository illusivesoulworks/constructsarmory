package c4.conarm.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import java.util.List;

public class TraitConservative extends AbstractArmorTrait {

    private static final String TAG_STORED_WATER = "stored_water";
    private static final int MAX_WATER = 200;
    private static final int DRINK_COST = 20;

    public TraitConservative() {
        super("conservative", TextFormatting.DARK_GREEN);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if(!world.isRemote) {

            if (world.isRainingAt(player.getPosition()) || player.isInWater()) {
                //Every second (20 ticks)
                if (world.getTotalWorldTime() % 20 == 0) {
                    storeWater(armor);
                }
            }
        }
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (evt.getSource().damageType.equals(DamageSource.STARVE.damageType)) {
            if (drinkWater(armor)) {
                evt.getEntity().playSound(SoundEvents.ENTITY_GENERIC_DRINK, 0.5F, 1.0F);
                evt.setCanceled(true);
            }
        }
        return super.onHurt(armor, player, source, damage, newDamage, evt);
    }

    private boolean drinkWater(ItemStack armor) {
        ModifierTagHolder tag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        ConservativeData data = tag.getTagData(ConservativeData.class);

        if(data.storedWater >= DRINK_COST) {
            data.storedWater -= DRINK_COST;
            tag.save();
            return true;
        }
        return false;
    }

    private void storeWater(ItemStack armor) {

        ModifierTagHolder tag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        ConservativeData data = tag.getTagData(ConservativeData.class);

        if(data.storedWater < MAX_WATER) {
            data.storedWater++;
            tag.save();
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        ConservativeData data = ModifierNBT.readTag(modifierTag, ConservativeData.class);
        String loc = String.format(LOC_Extra, getIdentifier());
        return ImmutableList.of(Util.translateFormatted(loc, data.storedWater));
    }

    public static class ConservativeData extends ModifierNBT {

        public int storedWater;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            storedWater = tag.getInteger(TAG_STORED_WATER);
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setInteger(TAG_STORED_WATER, storedWater);
        }
    }
}
