package org.snekker.jetpack.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.snekker.jetpack.Jetpack;

public class ModSounds {
    private ModSounds() {

    }

    public static final SoundEvent JETPACK_SOUND = registerSoundEvent("jetpack_sound");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Jetpack.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {

    }


}
