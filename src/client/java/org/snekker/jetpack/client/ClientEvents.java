package org.snekker.jetpack.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;

public class ClientEvents {
    public static void registerEvents(){
        ClientTickEvents.START_CLIENT_TICK.register(ClientEvents::onTick);

    }

    private static void onTick(MinecraftClient minecraftClient) {
        if (minecraftClient.player == null) {
            return;
        }
        var jetpack = minecraftClient.player.getEquippedStack(EquipmentSlot.CHEST);
        if(!jetpack.isEmpty() && jetpack.isOf(ModItems.JETPACK) && minecraftClient.player.getAbilities().flying){
            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);
            if(fuel > 0){
                fuel -= 1;
                jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, fuel);

            } else {
                minecraftClient.player.getAbilities().flying = false;
            }

        }
    }
}
