/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.client.gui;

import c4.conarm.common.tileentities.TileArmorForge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiArmorForge extends GuiArmorStation {

    public GuiArmorForge(InventoryPlayer player, World world, BlockPos pos, TileArmorForge tile) {
        super(player, world, pos, tile);
        metal();
    }
}
