/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.events;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.traits.IArmorAbility;
import c4.conarm.lib.traits.IArmorTrait;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class ArmorEvents {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent evt) {

        if (evt.phase == TickEvent.Phase.END) {

            ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(evt.player);

            if (armorAbilities != null) {
                for (String identifier : armorAbilities.getAbilityMap().keySet()) {
                    ITrait trait = TinkerRegistry.getTrait(identifier);
                    if (trait != null && trait instanceof IArmorTrait) {
                        ((IArmorTrait) trait).onAbilityTick(armorAbilities.getAbilityLevel(identifier), evt.player.world, evt.player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent evt) {
        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(evt.player);
        if (armorAbilities != null) {
            armorAbilities.clearAllAbilities();
        }
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent evt) {

        if (!(evt.getEntityLiving() instanceof EntityPlayer) || evt.getSlot() == EntityEquipmentSlot.OFFHAND || evt.getSlot() == EntityEquipmentSlot.MAINHAND) {
            return;
        }

        EntityPlayer player = (EntityPlayer) evt.getEntityLiving();

        ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(player);

        if (armorAbilities == null) {
            return;
        }

        armorAbilities.clearAllAbilities();

        for (ItemStack stack : player.getArmorInventoryList()) {
            if (stack.getItem() instanceof TinkersArmor && !ToolHelper.isBroken(stack)) {
                NBTTagList list = TagUtil.getTraitsTagList(stack);
                for (int i = 0; i < list.tagCount(); i++) {
                    ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                    if (trait != null && trait instanceof IArmorAbility) {
                        NBTTagCompound modifierTag = new NBTTagCompound();
                        NBTTagList tagList = TagUtil.getModifiersTagList(TagUtil.getTagSafe(stack));
                        int index = TinkerUtil.getIndexInList(tagList, trait.getIdentifier());
                        if(index >= 0) {
                            modifierTag = tagList.getCompoundTagAt(index);
                        }
                        ArmorHelper.addArmorAbility(player, trait.getIdentifier(), ((IArmorAbility) trait).getAbilityLevel(modifierTag));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerJump(LivingEvent.LivingJumpEvent evt) {
        if (evt.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntity();
            for (ItemStack stack : player.getArmorInventoryList()) {
                if (stack.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(stack)) {
                        NBTTagList list = TagUtil.getTraitsTagList(stack);
                        for (int i = 0; i < list.tagCount(); i++) {
                            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                            if (trait != null && trait instanceof IArmorTrait) {
                                ((IArmorTrait) trait).onJumping(stack, player, evt);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerPickUp(EntityItemPickupEvent evt) {

        EntityItem entityItem = evt.getItem();
        EntityPlayer player = evt.getEntityPlayer();

        for (ItemStack stack : player.getArmorInventoryList()) {
            if (stack.getItem() instanceof TinkersArmor) {
                if (!ToolHelper.isBroken(stack)) {
                    NBTTagList list = TagUtil.getTraitsTagList(stack);
                    for (int i = 0; i < list.tagCount(); i++) {
                        ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                        if (trait != null && trait instanceof IArmorTrait) {
                            ((IArmorTrait) trait).onItemPickup(stack, entityItem, evt);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerHurt(LivingHurtEvent evt) {

        if (evt.getEntity() instanceof EntityPlayer) {

            float damage = evt.getAmount();
            float newDamage = damage;

            EntityPlayer player = (EntityPlayer) evt.getEntity();
            for (ItemStack stack : player.getArmorInventoryList()) {
                if (stack.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(stack)) {
                        NBTTagList list = TagUtil.getTraitsTagList(stack);
                        for (int i = 0; i < list.tagCount(); i++) {
                            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                            if (trait != null && trait instanceof IArmorTrait) {
                                newDamage = ((IArmorTrait) trait).onHurt(stack, player, evt.getSource(), damage, newDamage, evt);
                            }
                        }
                    }
                }
            }

            evt.setAmount(Math.max(0, newDamage));
        }
    }

    @SubscribeEvent
    public void playerKnockback(LivingKnockBackEvent evt) {

        if (evt.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) evt.getEntity();
            for (ItemStack stack : player.getArmorInventoryList()) {
                if (stack.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(stack)) {
                        NBTTagList list = TagUtil.getTraitsTagList(stack);
                        for (int i = 0; i < list.tagCount(); i++) {
                            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                            if (trait != null && trait instanceof IArmorTrait) {
                                ((IArmorTrait) trait).onKnockback(stack, player, evt);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerFall(LivingFallEvent evt) {

        if (evt.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) evt.getEntity();
            for (ItemStack stack : player.getArmorInventoryList()) {
                if (stack.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(stack)) {
                        NBTTagList list = TagUtil.getTraitsTagList(stack);
                        for (int i = 0; i < list.tagCount(); i++) {
                            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                            if (trait != null && trait instanceof IArmorTrait) {
                                ((IArmorTrait) trait).onFalling(stack, player, evt);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerDamaged(LivingDamageEvent evt) {

        if (evt.getEntity() instanceof EntityPlayer) {

            float damage = evt.getAmount();
            float newDamage = damage;

            EntityPlayer player = (EntityPlayer) evt.getEntity();
            for (ItemStack stack : player.getArmorInventoryList()) {
                if (stack.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(stack)) {
                        NBTTagList list = TagUtil.getTraitsTagList(stack);
                        for (int i = 0; i < list.tagCount(); i++) {
                            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
                            if (trait != null && trait instanceof IArmorTrait) {
                                newDamage = ((IArmorTrait) trait).onDamaged(stack, player, evt.getSource(), damage, newDamage, evt);
                            }
                        }
                    }
                }
            }

            evt.setAmount(Math.max(0, newDamage));
        }
    }

    @SubscribeEvent
    public void onRepair(ArmoryEvent.OnRepair event) {
        ItemStack armor = event.itemStack;

        NBTTagList list = TagUtil.getTraitsTagList(armor);
        for(int i = 0; i < list.tagCount(); i++) {
            ITrait trait = TinkerRegistry.getTrait(list.getStringTagAt(i));
            if(trait != null) {
                trait.onRepair(armor, event.amount);
            }
        }
    }
}
