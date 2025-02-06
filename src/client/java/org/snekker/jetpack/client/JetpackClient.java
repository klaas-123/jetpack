package org.snekker.jetpack.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.network.SetFuelPayload;
import org.snekker.jetpack.sound.ModSounds;


public class JetpackClient implements ClientModInitializer {

    private static KeyBinding keyBinding;
    //private static PlayerEntity player1;

    @Override
    public void onInitializeClient() {

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jetpack.spook", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_SPACE, // The keycode of the key
                "category.jetpack.test" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) {
                return;
            }
            var jetpack = client.player.getEquippedStack(EquipmentSlot.CHEST);
            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);
            if (keyBinding.isPressed() && !client.player.isOnGround() && jetpack.isOf(ModItems.JETPACK) && !jetpack.isEmpty() && fuel > 0) {

                Vec3d currentVelocity = client.player.getVelocity();
                if (currentVelocity.y < 0) {
                    client.player.setVelocity(currentVelocity.add(0, 0.25, 0));
                } else if (currentVelocity.y < 0.3) {
                    client.player.setVelocity(currentVelocity.add(0, 0.1, 0));
                }

                var vec = client.player.getRotationVector();
                var x = client.player.getX() - vec.getX();
                var y = client.player.getY() + 0.5;
                var z = client.player.getZ() - vec.getZ();
                    client.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1f , 1f);
                    client.world.playSound(client.player, x, y, z, ModSounds.JETPACK_SOUND, SoundCategory.PLAYERS);
                fuel -= 1;
                jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, fuel);
                ClientPlayNetworking.send(new SetFuelPayload(fuel));
            }
        });


    }

}
