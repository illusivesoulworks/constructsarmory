/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers.accessories;

import c4.conarm.client.fx.ParticleSoul;
import c4.conarm.common.armor.traits.TraitUtils;
import c4.conarm.common.armor.utils.ArmorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;

import java.util.List;

public class ModTravelSoul extends AbstractTravelGoggles {

    public ModTravelSoul() {
        super(VisionType.SOUL);
    }

    @Override
    public void onKeybinding(ItemStack armor, EntityPlayer player) {
        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        if (!data.goggles) {
            ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 3, player, EntityEquipmentSlot.HEAD.getIndex());
        }
        super.onKeybinding(armor, player);
    }

    @Override
    public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {

        ModifierTagHolder modtag = ModifierTagHolder.getModifier(armor, getModifierIdentifier());
        GogglesData data = modtag.getTagData(GogglesData.class);
        if (data.goggles) {
            if (world.isRemote) {
                int radius = 20;
                BlockPos pos = player.getPosition();
                List<Entity> entities = player.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), TraitUtils.IS_LIVING);
                for (Entity entity : entities) {
                    if (entity instanceof EntityLivingBase && random.nextInt(5) == 0) {
                        Particle soul = new ParticleSoul(world, entity.posX, entity.posY + entity.height / 1.25D, entity.posZ, (float) Math.sqrt(((EntityLivingBase) entity).getHealth()));
                        soul.setAlphaF(0.35F);
                        Minecraft.getMinecraft().effectRenderer.addEffect(soul);
                    }
                }
            } else {
                ArmorHelper.damageArmor(armor, DamageSource.GENERIC, 1, player, EntityEquipmentSlot.HEAD.getIndex());
            }
        }
    }
}
