package c4.conarm.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.shared.TinkerCommons;

public class TraitBaconlicious extends AbstractArmorTrait {

    private static final float chance = 0.025F;

    public TraitBaconlicious() {
        super("baconlicious", 0xffaaaa);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {

        dropBacon(player.getEntityWorld(), player.posX, player.posY, player.posZ);

        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    private void dropBacon(World world, double x, double y, double z) {
        if(!world.isRemote && random.nextFloat() < chance) {
            EntityItem entity = new EntityItem(world, x, y, z, TinkerCommons.bacon.copy());
            world.spawnEntity(entity);
        }
    }
}
