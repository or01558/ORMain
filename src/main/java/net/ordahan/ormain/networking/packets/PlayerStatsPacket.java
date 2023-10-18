package net.ordahan.ormain.networking.packets;

import classes.PlayerStats;
import classes.PlayerStatsProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.ordahan.ormain.utils.Tags;
import java.util.function.Supplier;

//with help from Kapitencraft
public class PlayerStatsPacket {
    private final CompoundTag compoundTag;

    public PlayerStatsPacket(CompoundTag compoundTag) {
          this.compoundTag = compoundTag;
    }

    public PlayerStatsPacket(FriendlyByteBuf buf) {
       this(Tags.parseString(buf.readUtf()));
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(Tags.parseCompoundTag(compoundTag));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        ServerPlayer player = context.getSender();
        PlayerStats playerStats = new PlayerStats();
        playerStats.loadNBTData(compoundTag);

        player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(oldStats -> {
            oldStats.copyFrom(playerStats);
            CompoundTag compoundTag = new CompoundTag();
            oldStats.saveNBTData(compoundTag);
        });

        return true;
    }
}
