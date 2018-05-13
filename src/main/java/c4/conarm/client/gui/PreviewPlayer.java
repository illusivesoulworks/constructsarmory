package c4.conarm.client.gui;

import c4.conarm.lib.utils.ConstructUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class PreviewPlayer extends AbstractClientPlayer {

    private static final GameProfile previewProfile = new GameProfile(UUID.fromString("79240efc-40f7-45a5-b8f9-4dc3e5cfc34a"), ConstructUtils.getPrefixedName("armor_preview"));

    public PreviewPlayer(World world)
    {
        super(world, previewProfile);
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
    @Override public void onDeath(@Nonnull DamageSource source){ }
    @Override public void onUpdate(){ }
    @Override public Entity changeDimension(int dim, @Nonnull ITeleporter teleporter){ return this; }
    @Override
    @Nullable
    protected NetworkPlayerInfo getPlayerInfo()
    {
        return Minecraft.getMinecraft().getConnection().getPlayerInfo(Minecraft.getMinecraft().player.getUniqueID());
    }
}
