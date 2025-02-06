package org.snekker.jetpack.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class JetpackClient implements ClientModInitializer {

    private static KeyBinding keyBinding;
    private static PlayerEntity player;

    @Override
    public void onInitializeClient() {

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jetpack.spook", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                "category.jetpack.test" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                client.player.sendMessage(Text.literal("Key 1 was pressed!"), false);
                //player.getAbilities().allowFlying;
                    client.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1f , 1f);
                    client.world.playSound(client.player, x, y, z, ModSounds.JETPACK_SOUND, SoundCategory.PLAYERS);
            }
        });

        ClientEvents.registerEvents();

    }

}
