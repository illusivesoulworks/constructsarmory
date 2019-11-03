/*
 * Copyright (c) 2018-2019 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TraitAmbitious extends AbstractArmorTrait {

    public TraitAmbitious() {
        super("ambitious", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingXPDrop(LivingExperienceDropEvent evt) {
        int xpToDrop = evt.getDroppedExperience();

        if (xpToDrop > 0) {
            int addXP = getXpToAdd(evt.getAttackingPlayer());

            if (addXP > 0) {
                evt.setDroppedExperience(addXP + xpToDrop);
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent evt) {
        int xpToDrop = evt.getExpToDrop();

        if (xpToDrop > 0) {
            int addXP = getXpToAdd(evt.getPlayer());

            if (addXP > 0) {
                evt.setExpToDrop(xpToDrop + addXP);
            }
        }
    }

    private static int getXpToAdd(EntityPlayer player) {
        int level = (int) ArmorHelper.getArmorAbilityLevel(player, ArmorTraits.ambitious.identifier);
        return level > 0 ? random.nextInt(level * 2 + 1) + 2 : 0;
    }
}
