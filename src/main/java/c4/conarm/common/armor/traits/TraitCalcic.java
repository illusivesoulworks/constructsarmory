/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitCalcic extends AbstractArmorTrait {

    public TraitCalcic() {
        super("calcic", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemUse (LivingEntityUseItemEvent.Start evt) {
        if (evt.getItem().getItem() instanceof ItemBucketMilk && evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            for (ItemStack armor : player.getArmorInventoryList()) {
                if (armor.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(armor)) {
                        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), this.getModifierIdentifier())) {
                            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
                            Data data = modtag.getTagData(Data.class);
                            data.milk = true;
                            modtag.save();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void finishMilk (LivingEntityUseItemEvent.Finish evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = 0;
            for (ItemStack armor : player.getArmorInventoryList()) {
                if (armor.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(armor)) {
                        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), this.getModifierIdentifier())) {
                            ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
                            Data data = modtag.getTagData(Data.class);
                            if (data.milk) {
                                ArmorHelper.healArmor(armor, 10, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                                level++;
                                data.milk = false;
                                modtag.save();
                            }
                        }
                    }
                }
            }
            if (level > 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100 * level, level - 1));
            }
        }
    }

    public static class Data extends ModifierNBT {

        boolean milk;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            milk = tag.getBoolean("milk");
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setBoolean("milk", milk);
        }
    }
}
