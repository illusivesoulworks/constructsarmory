package c4.conarm.common;

import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.ConstructUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.shared.TinkerCommons;

public class PlayerDataEvents {

    public static final String TAG_PLAYER_HAS_BOOK = ConstructUtils.getPrefixedName("spawned_book");

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(Config.spawnWithBook) {
            NBTTagCompound playerData = event.player.getEntityData();
            NBTTagCompound data = TagUtil.getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);

            if(!data.getBoolean(TAG_PLAYER_HAS_BOOK)) {
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(ConstructsArmor.book));
                data.setBoolean(TAG_PLAYER_HAS_BOOK, true);
                playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
            }
        }
    }
}
