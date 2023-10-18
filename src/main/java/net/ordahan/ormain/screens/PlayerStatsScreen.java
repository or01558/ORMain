package net.ordahan.ormain.screens;

import classes.PlayerStats;
import classes.PlayerStatsData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.ordahan.ormain.networking.packets.ModMessages;
import net.ordahan.ormain.networking.packets.PlayerStatsPacket;
import static net.ordahan.ormain.ORMain.MODID;

public class PlayerStatsScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui." + MODID + ".player_stats_screen");
    private static final Component CLOSE_BUTTON =
            Component.translatable("gui." + MODID + ".player_stats.button.add_button");

    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/gray_background.png");

    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private Button button;

    public PlayerStatsScreen() {
        super(TITLE);
        this.imageWidth = 250;
        this.imageHeight = 250;
    }

    private void updatePlayerStats() {
        if (this.minecraft == null || this.minecraft.player == null) return;

        PlayerStats playerStats = PlayerStatsData.getPlayerStats();
        playerStats.addHealth(5.0);

        CompoundTag tag = new CompoundTag();
        playerStats.saveNBTData(tag);

        ModMessages.sendToServer(new PlayerStatsPacket(tag));

    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;


        if (this.minecraft == null) return;

        Level level = this.minecraft.level;
        if (level == null) return;

        this.button = addRenderableWidget(new Button(this.imageWidth / 2, this.imageHeight, 75, 20, CLOSE_BUTTON, this::onButtonClicked));
    }

    private void onButtonClicked(Button button) {
        updatePlayerStats();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.drawString(poseStack, this.font, "Health: " + PlayerStatsData.getPlayerStats().getMAX_HEALTH(), this.leftPos, this.topPos - 5, 0xFF0000);

        this.drawString(poseStack, this.font, TITLE, this.imageWidth / 2, this.topPos, 0xFF0000);
    }

}
