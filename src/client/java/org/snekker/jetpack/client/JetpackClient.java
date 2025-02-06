package org.snekker.jetpack.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.entity.EquipmentSlot;
import org.lwjgl.glfw.GLFW;
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
                    client.player.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1f , 1f);
                    client.world.playSound(client.player, x, y, z, ModSounds.JETPACK_SOUND, SoundCategory.PLAYERS);
                fuel -= 1;
                jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, fuel);
                ClientPlayNetworking.send(new SetFuelPayload(fuel));
            }
        });


    }

}
