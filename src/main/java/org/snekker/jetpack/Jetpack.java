package org.snekker.jetpack;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snekker.jetpack.block.JetpackBlockEntityTypes;
import org.snekker.jetpack.block.ModBlocks;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.enchantments.ModEnchantmentEffects;
import org.snekker.jetpack.network.SetFuelPayload;
import org.snekker.jetpack.sound.ModSounds;

import static org.snekker.jetpack.ModItems.CUSTOM_ITEM_GROUP;
import static org.snekker.jetpack.ModItems.CUSTOM_ITEM_GROUP_KEY;

public class Jetpack implements ModInitializer {
    public static final String MOD_ID = "jetpack";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String id) {
        return Identifier.of(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        JetpackBlockEntityTypes.registerBlockEntityTypes();
        ModSounds.registerSounds();
        ModEnchantmentEffects.registerModEnchantmentEffects();


        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);


        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.JETPACK);
            itemGroup.add(ModItems.JET_ENGINE);
            itemGroup.add(ModItems.THRUSTER);
            itemGroup.add(ModItems.PROPELLER);
            itemGroup.add(ModItems.FUEL_CELL);
            itemGroup.add(ModItems.EMPTY_FUEL_CELL);
            itemGroup.add(ModBlocks.RECHARGE_STATION);
        });

        PayloadTypeRegistry.playC2S().register(SetFuelPayload.ID, SetFuelPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SetFuelPayload.ID, Jetpack::handleSetFuelPayload);


    }

    private static void handleSetFuelPayload(SetFuelPayload setFuelPayload, ServerPlayNetworking.Context context) {
        context.server().execute(()-> {
            var jetpack = context.player().getEquippedStack(EquipmentSlot.CHEST);
            if (jetpack.isOf(ModItems.JETPACK)){
                jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, setFuelPayload.fuel());
            }
        });
    }

}