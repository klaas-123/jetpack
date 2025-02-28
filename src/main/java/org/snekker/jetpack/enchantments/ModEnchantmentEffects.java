package org.snekker.jetpack.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.enchantments.custom.DashEnchantmentEffect;

public class ModEnchantmentEffects {
    public static final RegistryKey<Enchantment> LEAPING = of("leaping");
    public static MapCodec<DashEnchantmentEffect> DASH_EFFECT = register("dash_effect", DashEnchantmentEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(Jetpack.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Jetpack.MOD_ID, id), codec);
    }

    public static void registerModEnchantmentEffects() {
        Jetpack.LOGGER.info("Registering EnchantmentEffects for" + Jetpack.MOD_ID);
    }
}


