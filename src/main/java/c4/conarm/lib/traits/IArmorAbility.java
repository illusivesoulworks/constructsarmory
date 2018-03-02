package c4.conarm.lib.traits;

import net.minecraft.nbt.NBTTagCompound;

public interface IArmorAbility {

    int getAbilityLevel(NBTTagCompound modifierTag);
}
