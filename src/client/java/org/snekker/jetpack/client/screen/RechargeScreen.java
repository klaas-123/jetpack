package org.snekker.jetpack.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.block.RechargeStation;
import org.snekker.jetpack.screens.RechargeStationScreenHandler;

public class RechargeScreen extends HandledScreen<RechargeStationScreenHandler> {

    public static final Identifier BACKGROUND_TEXTURE = Jetpack.id("textures/gui/container/recharge_station.png");

    public RechargeScreen(RechargeStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    private int originX;
    private int originY;

    @Override
    protected void init() {
        super.init();

        originX = (width - backgroundWidth) / 2;
        originY = (height - backgroundHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, originX, originY, 0, 0, backgroundWidth, backgroundHeight, 176, 166);
        /*if (handler.isCharging()) {
            int progress = MathHelper.ceil(this.handler.getChargeProgress() * 13.0F) + 1;
            context.drawGuiTexture(RenderLayer::getGuiTextured, CHARGE_PROGRESS_TEXTURE, 14, 14, 0, 14 - progress, originX + 82, originY + 56 + 14 - progress, 14, progress);
            sparkles = sparkles + 1;
            if (sparkles > 199) {
                sparkles = 0;
            }
            context.drawGuiTexture(RenderLayer::getGuiTextured, SPARKLES_TEXTURE, 24, 120, 0, (int)Math.floor(sparkles / 40.0) * 24, originX + 80, originY + 21, 24, 24);
        }*/
    }


}
