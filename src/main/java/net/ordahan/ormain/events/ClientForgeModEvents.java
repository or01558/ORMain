package net.ordahan.ormain.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ordahan.ormain.keybinds.KeyBindings;
import net.ordahan.ormain.screens.PlayerStatsScreen;
import net.ordahan.ormain.screens.WeaponsScreen;
import net.ordahan.ormain.utils.Screens;

import static net.ordahan.ormain.ORMain.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ClientForgeModEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.TOGGLE_WEAPONS_SCREEN_KEY.consumeClick()) {
            Screens.toggleScreen(new WeaponsScreen());
        } else if (KeyBindings.TOGGLE_PLAYER_STATS_SCREEN_KEY.consumeClick()) {
            Screens.toggleScreen(new PlayerStatsScreen());

        }
    }
}
