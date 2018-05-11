package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class TraitSubterranean extends AbstractArmorTrait {

    public TraitSubterranean() {
        super("subterranean", TextFormatting.DARK_GRAY);
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        World world = player.getEntityWorld();
        double belowSea = world.getSeaLevel() - player.posY;
        if (belowSea > 0) {
            if (random.nextFloat() < belowSea / 100) {
                return 0;
            } else {
                return newDamage;
            }
        }
        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }
}
