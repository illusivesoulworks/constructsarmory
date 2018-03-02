package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitTasty extends AbstractArmorTrait {

    public static final int EAT_COST = 5;

    public TraitTasty() {
        super("tasty", TextFormatting.RED);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if(!player.getEntityWorld().isRemote) {

            FoodStats foodStats = player.getFoodStats();

            if (!foodStats.needFood()) {
                return;
            }

            float chance = 0.002F;

            if (player.getHealth() < player.getMaxHealth()) {
                chance += 0.008F;
            }

            if (foodStats.getFoodLevel() >= 10) {
                if (random.nextFloat() < chance) {
                    eatToRepair(armor, player);
                }
            } else {
                chance += (5 - foodStats.getFoodLevel()) * 0.0025f;
                chance -= foodStats.getSaturationLevel() * 0.005f;

                if (random.nextFloat() < chance) {
                    eatToRepair(armor, player);
                }
            }
        }
    }

    private void eatToRepair(ItemStack armor, EntityPlayer player) {

        if (!ToolHelper.isBroken(armor) && ToolHelper.getCurrentDurability(armor) >= EAT_COST) {
            player.getFoodStats().addStats(1, 0);
            player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.8f, 1.0f);
            ArmorHelper.damageArmor(armor, DamageSource.STARVE, EAT_COST, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        }
    }
}
