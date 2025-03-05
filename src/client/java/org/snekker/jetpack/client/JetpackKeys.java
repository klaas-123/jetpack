package org.snekker.jetpack.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.item.JetpackItem;
import org.snekker.jetpack.network.SetFuelPayload;
import org.snekker.jetpack.sound.ModSounds;

import java.util.Timer;
import java.util.TimerTask;


@Environment(EnvType.CLIENT)
public class JetpackKeys {
    private static KeyBinding toggleBinding;
    private static KeyBinding powerBinding;
    private static KeyBinding spaceBinding;

    public static void registerKeybinds() {
        registerToggleActiveKeybind();
    }

    private static void registerToggleActiveKeybind() {
        toggleBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jetpack.hover",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_H,
                "category.jetpack.keys"
        ));
        powerBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jetpack.fly",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_J,
                "category.jetpack.keys"
        ));
        spaceBinding = KeyBindingRegistry.register(new KeyBinding(
                "key.jetpack.test",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_SPACE,
                "category.jetpack.keys"
        ));

        ClientTickEvents.START_CLIENT_TICK.register(JetpackKeys::onClientTick);
    }
    private static void onClientTick(MinecraftClient client){

        if (client.player == null || client.world == null) {
            return;
        }

        var jetpack = client.player.getEquippedStack(EquipmentSlot.CHEST);
        var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);
        Vec3d currentVelocity = client.player.getVelocity();




        if (spaceBinding.isPressed()) {
            var playerY = client.player.getY();

            if (jetpack.isOf(ModItems.JETPACK) && !jetpack.isEmpty() && fuel > 0) {
                if (playerY > client.player.getY()){
                    client.player.setVelocity(currentVelocity.add(0, 0.1, 0));
                }
                if (playerY < client.player.getY()){
                    client.player.setVelocity(currentVelocity.add(0, -0.1, 0));
                }

            }
        }

    }
}