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

    private static final int CHANCE = 120;

    public TraitEcological() {
        super("ecological", TextFormatting.GREEN);
    }

    @Override
    public void onUpdate(ItemStack armor, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(!world.isRemote && entity instanceof EntityPlayer && random.nextInt(20 * CHANCE) == 0) {
            ArmorHelper.healArmor(armor, 1, (EntityPlayer) entity, EntityLiving.getSlotForItemStack(armor).getIndex());
        }
    }
}
