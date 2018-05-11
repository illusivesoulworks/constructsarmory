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

    private static final float MODIFIER = 0.1F;

    public TraitPrideful() {
        super("prideful", TextFormatting.DARK_PURPLE);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
        if (player.getLastDamageSource() != null) {
            mods.addEffectiveness(MODIFIER);
            ArmorHelper.damageArmor(armor, source, 3, player, slot);
        }
        return mods;
    }
}
