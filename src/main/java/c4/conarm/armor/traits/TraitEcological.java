package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitEcological extends AbstractArmorTrait {

    private static final int CHANCE = 80;

    public TraitEcological() {
        super("ecological_armor", TextFormatting.GREEN);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
        if(!world.isRemote && random.nextInt(20 * CHANCE) == 0) {
            ArmorHelper.healArmor(armor, 1, player, EntityLiving.getSlotForItemStack(armor).getIndex());
        }
    }
}
