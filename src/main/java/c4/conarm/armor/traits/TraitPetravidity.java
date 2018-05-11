package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class TraitPetravidity extends AbstractArmorTrait {

    public TraitPetravidity() {
        super("petravidity", TextFormatting.RED);
    }

    @Override
    public void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt) {
        ItemStack stack = item.getItem();
        int toRepair = ToolHelper.getMaxDurability(armor) - ToolHelper.getCurrentDurability(armor);
        if (toRepair > 0 && stack.getItem() == Item.getItemFromBlock(Blocks.STONE)) {
            int count = stack.getCount();
            if (toRepair >= count) {
                ArmorHelper.healArmor(armor, count, evt.getEntityPlayer(), EntityLiving.getSlotForItemStack(armor).getIndex());
                item.setDead();
            } else {
                ArmorHelper.healArmor(armor, toRepair, evt.getEntityPlayer(), EntityLiving.getSlotForItemStack(armor).getIndex());
                item.getItem().shrink(toRepair);
            }
            evt.setCanceled(true);
        }
    }
}
