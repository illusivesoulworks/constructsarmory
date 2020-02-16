/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.client.gui;

import c4.conarm.lib.utils.ConstructUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class PreviewPlayer extends AbstractClientPlayer {

    private static final GameProfile previewProfile = new GameProfile(UUID.fromString("79240efc-40f7-45a5-b8f9-4dc3e5cfc34a"), ConstructUtils.getPrefixedName("armor_preview"));
    private AbstractClientPlayer player;

    public PreviewPlayer(World world, AbstractClientPlayer player)
    {
        super(world, previewProfile);
        this.player = player;
        this.getDataManager().set(PLAYER_MODEL_FLAG, player.getDataManager().get(PLAYER_MODEL_FLAG));
    }

    @Override
    @Nonnull
    public Vec3d getPositionVector(){ return new Vec3d(0, 0, 0); }
    @Override public boolean canUseCommand(int i, String s){ return false; }
    @Override public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar){}
    @Override public void sendMessage(ITextComponent component) {}
    @Override public void addStat(StatBase par1StatBase, int par2){}
    @Override public void openGui(@Nonnull Object mod, int modGuiId, @Nonnull World world, int x, int y, int z){}
    @Override public boolean isEntityInvulnerable(@Nonnull DamageSource source){ return true; }
    @Override public boolean canAttackPlayer(EntityPlayer player){ return false; }
    @Override public void onDeath(@Nonnull DamageSource source) {}
    @Override public Entity changeDimension(int dim, @Nonnull ITeleporter teleporter){ return this; }
    @Override
    @Nullable
    protected NetworkPlayerInfo getPlayerInfo()
    {
        NetHandlerPlayClient client = Minecraft.getMinecraft().getConnection();
        NetworkPlayerInfo info = null;
        if (client != null) {
            info = client.getPlayerInfo(this.player.getUniqueID());
        }
        return info;
    }
}
