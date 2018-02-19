package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

public class TraitCombustible extends AbstractArmorTrait {

    public TraitCombustible() {
        super("combustible", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {

        if (!evt.getSource().isFireDamage() && !evt.getSource().isExplosion()) {
            return;
        }

        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = ArmorHelper.getArmorAbilityLevel(player, this.identifier);
            if (level > 0) {
                double radius = 1.5D * level;
                BlockPos pos = player.getPosition();
                List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), TraitUtils.IS_LIVING);
                for (Entity entity : entities) {
                    if (attackEntitySecondary(DamageSource.causePlayerDamage(player).setFireDamage(), level, entity, true, false)) {
                        entity.setFire(1 + level);
                    }
                }
            }
        }
    }
}
