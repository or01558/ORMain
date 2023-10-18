package net.ordahan.ormain.events;

import classes.PlayerStats;
import classes.PlayerStatsProvider;
import net.ordahan.ormain.networking.packets.PlayerStatsSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ordahan.ormain.networking.packets.ModMessages;

import static net.ordahan.ormain.ORMain.LOGGER;
import static net.ordahan.ormain.ORMain.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerStatsProvider.PLAYER_STATS).isPresent()) {
                event.addCapability(new ResourceLocation(MODID, "properties"), new PlayerStatsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent((oldStore) -> {
                event.getEntity().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent((newStore) -> {
                    CompoundTag compoundTag = new CompoundTag();
                    newStore.saveNBTData(compoundTag);
                    ModMessages.sendToPlayer(new PlayerStatsSyncPacket(compoundTag), (ServerPlayer) event.getEntity());
                    newStore.copyFrom(oldStore);

                });
            });
            LOGGER.debug("adding modifiers");
            PlayerStats.addModifiers(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerStats.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {

        if (!event.getLevel().isClientSide) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(playerStats -> {
                    CompoundTag compoundTag = new CompoundTag();
                    playerStats.saveNBTData(compoundTag);
                    ModMessages.sendToPlayer(new PlayerStatsSyncPacket(compoundTag), player);
                });
                LOGGER.debug("adding modifiers");
                PlayerStats.addModifiers((Player) event.getEntity());
            }
        }
    }
}
