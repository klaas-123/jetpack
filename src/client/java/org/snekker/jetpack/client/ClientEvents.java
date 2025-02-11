package org.snekker.jetpack.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.network.SetFuelPayload;

import org.snekker.jetpack.client.JetpackClient;


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
            
            var x = minecraftClient.player.getX();
            var y = minecraftClient.player.getY() + 0.5;
            var z = minecraftClient.player.getZ();
            
            minecraftClient.world.addParticle(ParticleTypes.CLOUD, x, y, z, 0, -0.1, 0);
            minecraftClient.world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, -0.06, 0);



            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);
            if(fuel > 0){
                fuel -= 1;
                jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, fuel);
                ClientPlayNetworking.send(new SetFuelPayload(fuel));

            } else {
                minecraftClient.player.getAbilities().flying = false;
            }

        }
    }
}
