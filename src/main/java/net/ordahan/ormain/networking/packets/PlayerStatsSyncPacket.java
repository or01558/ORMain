package net.ordahan.ormain.networking.packets;

import classes.PlayerStats;
import classes.PlayerStatsData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.ordahan.ormain.utils.Tags;

import java.util.function.Supplier;
import static net.ordahan.ormain.ORMain.LOGGER;
public class PlayerStatsSyncPacket {
    private final CompoundTag compoundTag;

    public PlayerStatsSyncPacket(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public PlayerStatsSyncPacket(FriendlyByteBuf buf) {
        this(Tags.parseString(buf.readUtf()));
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(Tags.parseCompoundTag(compoundTag));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        PlayerStats playerStats = new PlayerStats();
        playerStats.loadNBTData(compoundTag);
        PlayerStatsData.set(playerStats);

        return true;
    }
}
