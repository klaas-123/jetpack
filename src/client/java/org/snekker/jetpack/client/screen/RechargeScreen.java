package org.snekker.jetpack.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.screens.RechargeStationScreenHandler;

public class RechargeScreen extends HandledScreen<RechargeStationScreenHandler> {

    public static final Identifier BACKGROUND_TEXTURE = Jetpack.id("textures/gui/container/recharge_station.png");
    public static final Identifier SMELT_PROGRESS_TEXTURE = Jetpack.id("container/recharge_station/smelt_progress");
    public static final Identifier MELT_PROGRESS_TEXTURE = Jetpack.id("container/recharge_station/melt_progress");

    public RechargeScreen(RechargeStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        playerInventory = inventory;
    }
    private final PlayerInventory playerInventory;
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

        var text = handler.getJetpackFuelStat();
        var width = getTextRenderer().getWidth(text);
        context.drawText(getTextRenderer(), text, originX + 230 - width, originY + 4, 1, true);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, originX, originY, 0, 0, backgroundWidth = 215, backgroundHeight, 215, 166);
        drawMouseoverTooltip(context, mouseX, mouseY);
        if (handler.isCharging()) {
            int progress = MathHelper.ceil(this.handler.getChargeProgress() * 13.0F);
            context.drawGuiTexture(RenderLayer::getGuiTextured, SMELT_PROGRESS_TEXTURE, 215, 166, 0, 50 - progress, originX, originY + 49 - progress, 176, progress);
            context.drawGuiTexture(RenderLayer::getGuiTextured, MELT_PROGRESS_TEXTURE, 215, 166, 0,  33, originX, originY + 32, 176, progress);
        }
    }


}
