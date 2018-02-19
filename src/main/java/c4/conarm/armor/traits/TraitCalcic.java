package c4.conarm.armor.traits;

import c4.conarm.ConstructsArmory;
import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
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
        CapabilityManager.INSTANCE.register(ICalcic.class, new Storage() , Calcic::new);
    }

    @SubscribeEvent
    public void startMilk(LivingEntityUseItemEvent.Start evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            if (evt.getItem().getItem() instanceof ItemBucketMilk) {
                ICalcic data = getCalcicData(player);
                if (data != null) {
                    data.setItem(evt.getItem().copy());
                }
            }
        }
    }

    @SubscribeEvent
    public void finishMilk (LivingEntityUseItemEvent.Finish evt) {
        if (evt.getResultStack().getItem() instanceof ItemBucket && evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            ICalcic data = getCalcicData(player);
            if (data != null) {
                ItemStack stack = data.getItem();
                if (stack.getItem() instanceof ItemBucketMilk) {
                    int level = 0;
                    for (ItemStack armor : player.getArmorInventoryList()) {
                        if (armor.getItem() instanceof ArmorCore) {
                            if (!ToolHelper.isBroken(armor)) {
                                if (TinkerUtil.hasTrait(TagUtil.getTagSafe(armor), getModifierIdentifier())) {
                                    ArmorHelper.healArmor(armor, 10, player, EntityLiving.getSlotForItemStack(armor).getIndex());
                                    level++;
                                }
                            }
                        }
                    }
                    if (level > 0) {
                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100 * level, level - 1));
                    }
                    data.setItem(ItemStack.EMPTY);
                }
            }
        }
    }

    @CapabilityInject(ICalcic.class)
    public static final Capability<ICalcic> CALCIC_CAP = null;

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> evt) {

        if (evt.getObject() instanceof EntityPlayer) {
            evt.addCapability(new ResourceLocation(ConstructsArmory.MODID, "calcic"), new Provider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone evt) {

        ICalcic old = getCalcicData(evt.getOriginal());
        ICalcic clone = getCalcicData(evt.getEntityPlayer());

        if (old != null && clone != null) {
            clone.setItem(old.getItem());
        }
    }

    public static ICalcic getCalcicData(EntityPlayer player) {

        return player != null && player.hasCapability(CALCIC_CAP, null) ? player.getCapability(CALCIC_CAP, null) : null;
    }

    public static class Calcic implements ICalcic {

        private ItemStack stack = ItemStack.EMPTY;

        public Calcic() {}

        public ItemStack getItem() {
            return this.stack;
        }

        public void setItem(ItemStack stack) {
            this.stack = stack;
        }
    }

    public interface ICalcic {

        ItemStack getItem();

        void setItem(ItemStack stack);
    }

    public static class Provider implements ICapabilitySerializable<NBTBase> {

        private ICalcic instance = CALCIC_CAP.getDefaultInstance();

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == CALCIC_CAP;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            return capability == CALCIC_CAP ? CALCIC_CAP.<T> cast(this.instance) : null;
        }

        @Override
        public NBTBase serializeNBT()
        {
            return CALCIC_CAP.getStorage().writeNBT(CALCIC_CAP, this.instance, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CALCIC_CAP.getStorage().readNBT(CALCIC_CAP, this.instance, null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<ICalcic> {

        @Override
        public NBTBase writeNBT(Capability<ICalcic> capability, ICalcic instance, EnumFacing side) {

            NBTTagCompound compound = new NBTTagCompound();
            NBTTagCompound tagcompound = new NBTTagCompound();
            ItemStack stack = instance.getItem();
            if (!stack.isEmpty()) {
                stack.writeToNBT(tagcompound);
            }

            compound.setTag("Calcic", tagcompound);

            return compound;
        }

        @Override
        public void readNBT(Capability<ICalcic> capability, ICalcic instance, EnumFacing side, NBTBase nbt) {

            NBTTagCompound compound = (NBTTagCompound) nbt;
            NBTTagCompound tagcompound = compound.getCompoundTag("Calcic");
            ItemStack stack = new ItemStack(tagcompound);
            if (!stack.isEmpty()) {
                instance.setItem(new ItemStack(tagcompound));
            }
        }
    }
}
