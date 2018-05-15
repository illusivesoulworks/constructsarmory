/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.lib.capabilities;

import c4.conarm.ConstructsArmory;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Map;

public class ArmorAbilityHandler {

    @CapabilityInject(IArmorAbilities.class)
    public static final Capability<IArmorAbilities> ARMOR_AB_CAP = null;

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> evt) {

        if (evt.getObject() instanceof EntityPlayer) {
            evt.addCapability(new ResourceLocation(ConstructsArmory.MODID, "armor_abilities"), new Provider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone evt) {

        IArmorAbilities old = getArmorAbilitiesData(evt.getOriginal());
        IArmorAbilities clone = getArmorAbilitiesData(evt.getEntityPlayer());

        if (old != null && clone != null) {
            clone.setAbilityMap(old.getAbilityMap());
        }
    }

    public static IArmorAbilities getArmorAbilitiesData(EntityPlayer player) {

        return player != null && player.hasCapability(ARMOR_AB_CAP, null) ? player.getCapability(ARMOR_AB_CAP, null) : null;
    }

    public static class ArmorAbilities implements IArmorAbilities {

        private Map<String, Integer> abilityMap = Maps.newHashMap();

        public ArmorAbilities() {}

        public void clearAllAbilities() {
            abilityMap.clear();
        }

        public Map<String, Integer> getAbilityMap() {
            return this.abilityMap;
        }

        public void setAbilityMap(Map<String, Integer> abilityMap) {
            this.abilityMap = abilityMap;
        }

        public void addAbility(String identifier, int amount) {
            abilityMap.merge(identifier, amount, (a, b) -> a + b);
        }

        public void removeAbility(String identifier, int amount) {
            if (abilityMap.get(identifier) != null) {

                int level = abilityMap.get(identifier) - amount;

                if (level <= 0) {
                    abilityMap.remove(identifier);
                } else {
                    abilityMap.replace(identifier, level);
                }
            }
        }

        public int getAbilityLevel(String identifier) {
            if (abilityMap.get(identifier) != null) {
                return abilityMap.get(identifier);
            } else {
                return 0;
            }
        }
    }

    public interface IArmorAbilities {

        Map<String, Integer> getAbilityMap();

        void clearAllAbilities();

        void setAbilityMap(Map<String, Integer> abilityMap);

        void addAbility(String identifier, int amount);

        void removeAbility(String identifier, int amount);

        int getAbilityLevel(String identifier);
    }

    public static class Provider implements ICapabilitySerializable<NBTBase> {

        private IArmorAbilities instance = ARMOR_AB_CAP.getDefaultInstance();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
            return capability == ARMOR_AB_CAP;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
            return capability == ARMOR_AB_CAP ? ARMOR_AB_CAP.<T> cast(this.instance) : null;
        }

        @Override
        public NBTBase serializeNBT()
        {
            return ARMOR_AB_CAP.getStorage().writeNBT(ARMOR_AB_CAP, this.instance, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            ARMOR_AB_CAP.getStorage().readNBT(ARMOR_AB_CAP, this.instance, null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<IArmorAbilities> {

        @Override
        public NBTBase writeNBT(Capability<IArmorAbilities> capability, IArmorAbilities instance, EnumFacing side) {

            NBTTagCompound compound = new NBTTagCompound();
            NBTTagCompound tagcompound = new NBTTagCompound();
            Map<String, Integer> abilityMap = instance.getAbilityMap();

            for (String identifier : abilityMap.keySet()) {
                tagcompound.setInteger(identifier, abilityMap.get(identifier));
            }

            compound.setTag("ArmorAbilities", tagcompound);

            return compound;
        }

        @Override
        public void readNBT(Capability<IArmorAbilities> capability, IArmorAbilities instance, EnumFacing side, NBTBase nbt) {

            NBTTagCompound compound = (NBTTagCompound) nbt;
            NBTTagCompound tagcompound = compound.getCompoundTag("ArmorAbilities");
            Map<String, Integer> abilityMap = Maps.newHashMap();

            for (String identifier : tagcompound.getKeySet()) {
                abilityMap.putIfAbsent(identifier, tagcompound.getInteger(identifier));
            }

            instance.setAbilityMap(abilityMap);
        }
    }
}
