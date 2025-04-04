package org.snekker.jetpack.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.client.screen.RechargeScreen;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.network.SetFuelPayload;
import org.snekker.jetpack.screens.ModScreens;
import org.snekker.jetpack.sound.ModSounds;

import java.util.Timer;
import java.util.TimerTask;




public class JetpackClient implements ClientModInitializer {

    static int fuelPercentage;
    private boolean cannotChainDash = true;
    private boolean canDash = false;
    private boolean canFly = false;
    private boolean onCooldown = false;
    private int soundcount = 64;

    @Override
    public void onInitializeClient() {
        JetpackKeys.registerKeybinds();
        HandledScreens.register(ModScreens.RECHARGE_STATION, RechargeScreen::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null || client.world == null || client.player.isInCreativeMode() || client.player.isSpectator()) {
                return;
            }


            var jetpack = client.player.getEquippedStack(EquipmentSlot.CHEST);
            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);

            if (!client.player.isOnGround()) {

                var vec = client.player.getRotationVector();
                var x = client.player.getX() - vec.getX();
                var y = client.player.getY() + 0.6;
                var z = client.player.getZ() - vec.getZ();

                double offset = 0.1;

                if (!client.player.input.playerInput.jump()) {
                    canFly = true;
                    soundcount = 64;
                    client.getSoundManager().stopSounds(ModSounds.JETPACK_SOUND.id(), SoundCategory.PLAYERS);
                    client.player.fallDistance = 0;
                }

                if (fuel == 0) {
                    client.getSoundManager().stopSounds(ModSounds.JETPACK_SOUND.id(), SoundCategory.PLAYERS);
                }

                if (!client.player.input.playerInput.sprint()){
                    canDash = true;
                }

                if (client.player.input.playerInput.sprint() && canDash && cannotChainDash && !onCooldown && fuel >= 100) {
                    Vec3d forward = client.player.getRotationVector().multiply(1.0, 0, 1.0).normalize().multiply(2.0); // Change 1.0 to your desired speed
                    client.player.setVelocity(forward);
                    fuel -= 100;
                    onCooldown = true;
                    canDash = false;
                    cannotChainDash = false;

                    Timer timer = new Timer();

                    timer.schedule(new TimerTask() {

                        public void run() {
                            onCooldown = false;
                            client.world.playSound(client.player, x, y, z, ModSounds.DASH_RESET, SoundCategory.PLAYERS, 100, 1);
                        }
                    }, 2*1000);


                }
                //client.player.setVelocity(client.player.getVelocity().getX(), client.player.input.playerInput.jump() ? 0.2D : client.player.getVelocity().getY(), client.player.getVelocity().getZ());

                if (client.player.input.playerInput.jump() && jetpack.isOf(ModItems.JETPACK) && !jetpack.isEmpty() && fuel > 0 && canFly) {

                    Vec3d currentVelocity = client.player.getVelocity();
                    Vec3d currentDirection = client.player.getRotationVecClient();

                    if (client.player.input.playerInput.sneak()){
                        client.player.setVelocity(currentVelocity.getX(), 0, currentVelocity.getZ());

                    } else {
                        if (currentVelocity.y < 0) {
                            client.player.setVelocity(currentVelocity.add(0, 0.25, 0));
                            client.player.fallDistance = 0;
                        } else if (currentVelocity.y < 0.3) {
                            client.player.setVelocity(currentVelocity.add(0, 0.1, 0));
                            client.player.fallDistance = 0;
                        }
                    }

                    if (client.player.isInFluid()) {
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset, y, z, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z + offset, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset, y, z + offset + offset, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset, y, z + offset, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset + offset, y, z, 0, -0.08, 0);
                        client.world.playSound(client.player, x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.PLAYERS);

                    } else if (client.player.isInvisible()) {
                        client.world.addParticle(ParticleTypes.END_ROD, x, y, z, 0, -0.02, 0);

                    } else {

                        client.world.addParticle(ParticleTypes.CLOUD, x, y, z, 0, -0.1, 0);
                        client.world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, -0.06, 0);

                        if (soundcount == 64) {
                            soundcount = 0;
                            client.world.playSound(client.player, x, y, z, ModSounds.JETPACK_SOUND, SoundCategory.PLAYERS);

                        }
                        soundcount += 1;
                    }
                    fuel -= 1;
                    jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, fuel);
                    ClientPlayNetworking.send(new SetFuelPayload(fuel));


                }


            } else if (client.player.isOnGround() || client.player.isClimbing()){
                canFly = false;
                cannotChainDash = true;
                canDash = false;

            }


        });


        HudRenderCallback.EVENT.register((context, renderTickCounter) -> {

            var client = MinecraftClient.getInstance();
            var jetpack = client.player.getEquippedStack(EquipmentSlot.CHEST);
            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);


            if (jetpack.isOf(ModItems.JETPACK)) {

                int textureFuelHeight = (int) Math.ceil(fuel / ((double) 2000 / 49));
                int fuelStartHeight = 61 - textureFuelHeight ;


               if (fuel/20.00 == 0) {
                    fuelPercentage = 0;
               } else {
                    fuelPercentage = (int) Math.max(fuel/20.00, 1);
               }

               if (fuelPercentage != 0 && fuelPercentage < 2) {
                   fuelStartHeight = 60;
               }


                context.enableScissor(-8, fuelStartHeight, 56, 62);
                context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of(Jetpack.MOD_ID, "renderer_fuel"), 18, 49, 0, 0, 15, 12, 18, 49);
                context.disableScissor();
                context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of(Jetpack.MOD_ID, "renderer_fuel_cell"), 64, 64, 0, 0, -8, 4, 64, 64);
                context.drawText(client.textRenderer, String.valueOf(fuelPercentage) + "%", 16, 72, 0xFFFFFFFF, true);
            }
        });

    }

}
