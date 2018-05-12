package c4.conarm.common.inventory;

import c4.conarm.common.tileentities.TileArmorForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.Sounds;

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
