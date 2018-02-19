package c4.conarm.armor.modifiers;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;

public class ModAmphibious extends ArmorModifierTrait {

    public ModAmphibious() {
        super("amphibious", 0x00ccff);
        addAspects(new ModifierAspect.SingleAspect(this));
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (player.isInWater()) {
            if (!player.isPotionActive(MobEffects.WATER_BREATHING)) {
                player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20));
            }
        }
    }
}
