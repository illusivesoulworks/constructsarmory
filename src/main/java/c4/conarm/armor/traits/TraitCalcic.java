package c4.conarm.armor.traits;

import c4.conarm.ConstructsArmory;
import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.Map;

public class TraitCalcic extends AbstractArmorTrait {

    public TraitCalcic() {
        super("calcic", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void finishMilk (LivingEntityUseItemEvent.Finish evt) {
        if (evt.getItem().getItem() instanceof ItemBucketMilk && evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = 0;
            for (ItemStack armor : player.getArmorInventoryList()) {
                if (armor.getItem() instanceof TinkersArmor) {
                    if (!ToolHelper.isBroken(armor)) {
                        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), this.getModifierIdentifier())) {
                            ArmorHelper.healArmor(armor, 10, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                            level++;
                        }
                    }
                }
            }
            if (level > 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100 * level, level - 1));
            }
        }
    }
}
