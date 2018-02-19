package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
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

    private static final float MODIFIER = 0.4F;

    public TraitPetravidity() {
        super("petravidity", TextFormatting.RED);
    }

//    @Override
//    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
//        if (source.getImmediateSource() instanceof EntityLivingBase) {
//            EntityLivingBase entityLivingBase = (EntityLivingBase) source.getImmediateSource();
//            if (isStoneWeapon(entityLivingBase.getHeldItemMainhand())) {
//                mods.addEffectiveness(MODIFIER);
//            }
//        }
//        return super.getModifications(player, mods, armor, source, damage, slot);
//    }

    @Override
    public void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt) {
        ItemStack stack = item.getItem();
        int toRepair = ToolHelper.getMaxDurability(armor) - ToolHelper.getCurrentDurability(armor);
        if (toRepair > 0 && stack.getItem() == Item.getItemFromBlock(Blocks.STONE)) {
            int count = stack.getCount();
            if (toRepair >= count) {
                ToolHelper.healTool(armor, count, evt.getEntityPlayer());
                item.setDead();
            } else {
                ToolHelper.healTool(armor, toRepair, evt.getEntityPlayer());
                item.getItem().shrink(toRepair);
            }
            evt.setCanceled(true);
        }
    }

//    @Override
//    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
//        if (source.getImmediateSource() instanceof EntityLivingBase) {
//            EntityLivingBase entityLivingBase = (EntityLivingBase) source.getImmediateSource();
//            if (isStoneWeapon(entityLivingBase.getHeldItemMainhand())) {
//                ToolHelper.healTool(armor, 10, player);
//            }
//        }
//        return super.onHurt(armor, player, source, damage, newDamage, evt);
//    }
//
//    private boolean isStoneWeapon(ItemStack stack) {
//        Item item = stack.getItem();
//        if (item instanceof ItemTool) {
//            if (((ItemTool) item).getToolMaterialName().equals("STONE")) {
//                return true;
//            }
//        } else if (item instanceof ItemSword) {
//            if (((ItemSword) item).getToolMaterialName().equals("STONE")) {
//                return true;
//            }
//        } else if (item instanceof TinkersItem) {
//            if (TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack)).contains(TinkerMaterials.stone)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
