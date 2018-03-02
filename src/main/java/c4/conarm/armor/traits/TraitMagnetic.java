package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TraitMagnetic extends AbstractArmorTrait {

    public TraitMagnetic() {
        super("magnetic", 0xdddddd);
    }

    @Override
    public void onAbilityTick(ArmorAbilityHandler.IArmorAbilities abilities, World world, EntityPlayer player) {

        slimeknights.tconstruct.tools.traits.TraitMagnetic.Magnetic.apply(player, 20, (int) ArmorHelper.getArmorAbilityLevel(player, this.getIdentifier()));
    }
}
