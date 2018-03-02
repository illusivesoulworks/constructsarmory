package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class TraitDramatic extends AbstractArmorTrait {

    protected static final UUID[] HEALTH_MODIFIERS = new UUID[]{
            UUID.fromString("e7017776-21d1-4740-99f2-a95517347216"),
            UUID.fromString("d1bb9747-e5ac-45d1-9e92-74c6c474fdc4"),
            UUID.fromString("81ddd7be-e2c7-4a97-b24f-8d2f1132d786"),
            UUID.fromString("254099a7-fc5a-41c0-937e-7d09db400733") };
    private static final double HEALTH_PER_LEVEL = 20.0D;

    public TraitDramatic() {
        super("dramatic", 0xff0000);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {
        if (evt.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int level = (int) ArmorHelper.getArmorAbilityLevel(player, this.identifier);
            if (level > 0) {
                if (player.getHealth() <= 5.0F && random.nextFloat() < (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth() * 0.1 * level) {
                    player.heal(level * 2);
                }
            }
        }
    }

//    @Override
//    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
//        mods.addEffectiveness((player.getMaxHealth() - player.getHealth()) / player.getMaxHealth() * 0.5F);
//        return super.getModifications(player, mods, armor, source, damage, slot);
//    }
//
//    @Override
//    public void getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
//        if (slot == EntityLiving.getSlotForItemStack(stack)) {
//            attributeMap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(HEALTH_MODIFIERS[slot.getIndex()], "Vigorous trait modifier", HEALTH_PER_LEVEL, 0));
//        }
//    }
}
