/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.common.events;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.utils.ConstructUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.utils.TagUtil;

public class PlayerDataEvents {

    public static final String TAG_PLAYER_HAS_BOOK = ConstructUtils.getPrefixedName("spawned_book");

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(Config.spawnWithBook) {
            NBTTagCompound playerData = event.player.getEntityData();
            NBTTagCompound data = TagUtil.getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);

            if(!data.getBoolean(TAG_PLAYER_HAS_BOOK)) {
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ConstructsRegistry.book));
                data.setBoolean(TAG_PLAYER_HAS_BOOK, true);
                playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
            }
        }
    }
}
