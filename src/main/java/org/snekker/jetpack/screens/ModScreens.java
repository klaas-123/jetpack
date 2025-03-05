package org.snekker.jetpack.screens;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import org.snekker.jetpack.Jetpack;

public class ModScreens {
    public static ScreenHandlerType<RechargeStationScreenHandler> RECHARGE_STATION = Registry.register(
            Registries.SCREEN_HANDLER,
            Jetpack.id("recharge_station"),
            new ScreenHandlerType<>(RechargeStationScreenHandler::new, FeatureSet.empty()));

    public static void registerScreenHandlers() {
    }
}
