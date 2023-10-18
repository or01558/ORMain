package net.ordahan.ormain.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String Category = "key.category.ormain.screens";

    public static final String KEY_TOGGLE_WEAPONS_SCREEN = "key.ormain.toggle_weapons_screen";
    public static final String KEY_TOGGLE_PLAYER_STATS_SCREEN = "key.ormain.toggle_player_stats_screen";


    public final static KeyMapping TOGGLE_WEAPONS_SCREEN_KEY = new KeyMapping(KEY_TOGGLE_WEAPONS_SCREEN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, Category);
    public final static KeyMapping TOGGLE_PLAYER_STATS_SCREEN_KEY = new KeyMapping(KEY_TOGGLE_PLAYER_STATS_SCREEN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Q, Category);
}
