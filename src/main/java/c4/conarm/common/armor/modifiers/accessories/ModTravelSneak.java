/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.common.armor.modifiers.ArmorModifiers;
import c4.conarm.lib.tinkering.TinkersArmor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class ModTravelSneak extends AbstractToggleAccessoryModifier {

    public ModTravelSneak() {
        super("travel_sneak", true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerVisibility(PlayerEvent.Visibility evt) {
        EntityPlayer player = evt.getEntityPlayer();
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (stack.getItem() instanceof TinkersArmor && !ToolHelper.isBroken(stack) && TinkerUtil.hasModifier(TagUtil.getTagSafe(stack), this.identifier)) {
            evt.modifyVisibility(0.5D);
        }
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        if (getToggleData(armor).toggle) {
            player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 100, -44, false, false));
        }

        super.onArmorTick(armor, world, player);
    }

    @Override
    public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {
        if (player.isPotionActive(MobEffects.INVISIBILITY)) {
            PotionEffect effect = player.getActivePotionEffect(MobEffects.INVISIBILITY);
            if (effect != null && effect.getAmplifier() == -44) {
                player.removePotionEffect(MobEffects.INVISIBILITY);
            }
        }
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST && super.canApplyCustom(stack);
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return otherModifier != ArmorModifiers.modConcealed;
    }

    @Override
    public boolean disableRendering(ItemStack armor, EntityLivingBase entityLivingBase) {
        return getToggleData(armor).toggle;
    }
}