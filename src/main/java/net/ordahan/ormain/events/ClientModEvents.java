package net.ordahan.ormain.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.ordahan.ormain.keybinds.KeyBindings;

import static net.ordahan.ormain.ORMain.MODID;
import static net.ordahan.ormain.ORMain.LOGGER;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        LOGGER.info("[ORMain] Loading Mod Content");
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.TOGGLE_WEAPONS_SCREEN_KEY);
        event.register(KeyBindings.TOGGLE_PLAYER_STATS_SCREEN_KEY);

    }
}
