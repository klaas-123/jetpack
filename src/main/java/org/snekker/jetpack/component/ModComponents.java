package org.snekker.jetpack.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.snekker.jetpack.Jetpack;

public class ModComponents {
    public static final ComponentType<Integer> JETPACK_FUEL_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Jetpack.MOD_ID, "jetpack_fuel"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
}
