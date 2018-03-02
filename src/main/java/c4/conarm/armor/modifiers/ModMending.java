package c4.conarm.armor.modifiers;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

public class ModMending extends ArmorModifierTrait {

    private static final int DELAY = 20 * 30; //Every 30s

    public ModMending() {
        super("mending", 0x43ab32, 3, 0);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onUpdate(ItemStack armor, World world, Entity entity, int itemSlot, boolean isSelected) {

        if(!world.isRemote && entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entity;
            if (!player.inventory.armorInventory.contains(armor)) {
                if (needsRepair(armor) && useXp(armor, world)) {
                    ArmorHelper.healArmor(armor, getDurabilityPerXP(armor), player, EntityLiving.getSlotForItemStack(armor).getIndex());
                }
            }
        }
    }

    @SubscribeEvent
    public void onPickupXp(PlayerPickupXpEvent evt) {

        EntityXPOrb entityXPOrb = evt.getOrb();

        for(ItemStack itemStack : evt.getEntityPlayer().getArmorInventoryList()) {
            if(!itemStack.isEmpty() && isMendingMossModified(itemStack)) {
                int stored = storeXp(entityXPOrb.xpValue, itemStack);
                entityXPOrb.xpValue -= stored;
            }
        }
    }

    private boolean isMendingMossModified(ItemStack itemStack) {
        return TinkerUtil.hasModifier(TagUtil.getTagSafe(itemStack), getModifierIdentifier());
    }

    private boolean needsRepair(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItemDamage() > 0 && !ToolHelper.isBroken(itemStack);
    }

    private int getDurabilityPerXP(ItemStack itemStack) {
        return 2 + ModifierTagHolder.getModifier(itemStack, getModifierIdentifier()).getTagData(slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data.class).level;
    }

    private int getMaxXp(int level) {
        if(level <= 1) {
            return 30;
        }

        return getMaxXp(level - 1) * 3;
    }

    private boolean canStoreXp(slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data data) {
        return data.storedXp < getMaxXp(data.level);
    }

    private int storeXp(int amount, ItemStack itemStack) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(itemStack, getModifierIdentifier());
        slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data data = modtag.getTagData(slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data.class);

        int change = 0;
        if(canStoreXp(data)) {
            int max = getMaxXp(data.level);
            change = Math.min(amount, max - data.storedXp);
            data.storedXp += change;
            modtag.save();
        }
        return change;
    }

    private boolean useXp(ItemStack itemStack, World world) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(itemStack, getModifierIdentifier());
        slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data data = modtag.getTagData(slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data.class);

        if(data.storedXp > 0 && world.getTotalWorldTime() - data.lastHeal > DELAY) {
            data.storedXp--;
            data.lastHeal = world.getTotalWorldTime();
            modtag.save();
            return true;
        }
        return false;
    }

    @Override
    public List<String> getExtraInfo(ItemStack armor, NBTTagCompound modifierTag) {
        slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data data = ModifierNBT.readTag(modifierTag, slimeknights.tconstruct.tools.modifiers.ModMendingMoss.Data.class);
        assert data != null;
        String loc = String.format(LOC_Extra, getIdentifier());
        return ImmutableList.of(
                Util.translateFormatted(loc, data.storedXp)
        );
    }
}
