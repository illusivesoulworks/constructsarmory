package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.potion.TinkerPotion;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class TraitPrideful extends AbstractArmorTrait {

//    public static TinkerPotion pridefulPotion = new PridefulPotion();
    private static final float MODIFIER = 0.2F;

    public TraitPrideful() {
        super("prideful", TextFormatting.DARK_PURPLE);
    }

//    @Override
//    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
//        if (player.getHealth() == player.getMaxHealth()) {
//            if (damage > player.getMaxHealth()) {
//                ArmorHelper.damageArmor(armor, source, 3, player, EntityLiving.getSlotForItemStack(armor).getIndex());
//                newDamage -= damage * MODIFIER;
//            }
//        }
//
//        return newDamage;
//    }

    @Override
    public void onAbilityTick(ArmorAbilityHandler.IArmorAbilities abilities, World world, EntityPlayer player) {
        if (!world.isRemote && player.ticksExisted % (20 * 20 / ArmorHelper.getArmorAbilityLevel(player, this.identifier)) == 0) {
            player.clearActivePotions();
        }
    }

//    @Override
//    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
//        if (player.isPotionActive(pridefulPotion)) {
//            mods.addEffectiveness(pridefulPotion.getLevel(player) * MODIFIER);
//        }
//        return super.getModifications(player, mods, armor, source, damage, slot);
//    }
//
//    @Override
//    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
//        if (player.isPotionActive(pridefulPotion)) {
//            return super.onArmorDamage(armor, source, damage, newDamage, player, slot) + (int) (pridefulPotion.getLevel(player) / 3F);
//        }
//        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
//    }
}
