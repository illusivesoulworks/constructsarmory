package c4.conarm.armor.common.inventory;

import c4.conarm.armor.common.tileentities.TileArmorForge;
import c4.conarm.armor.common.tileentities.TileArmorStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.tools.common.tileentity.TileToolStation;

import java.util.Set;

public class ContainerArmorForge extends ContainerArmorStation {

    public ContainerArmorForge(InventoryPlayer playerInventory, TileArmorForge tile) {
        super(playerInventory, tile);
        this.isForge = true;
    }

    @Override
    protected void playCraftSound(EntityPlayer player) {
        Sounds.playSoundForAll(player, SoundEvents.BLOCK_ANVIL_USE, 0.9f, 0.95f + 0.2f * TConstruct.random.nextFloat());
    }
}
