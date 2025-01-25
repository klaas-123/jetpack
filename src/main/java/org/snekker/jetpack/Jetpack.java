package org.snekker.jetpack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static org.snekker.jetpack.ModItems.CUSTOM_ITEM_GROUP;
import static org.snekker.jetpack.ModItems.CUSTOM_ITEM_GROUP_KEY;

public class Jetpack implements ModInitializer {
    public static final String MOD_ID = "jetpack";

    @Override
    public void onInitialize() {
        ModItems.initialize();

        // Register the group.
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

// Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.JETPACK);
            itemGroup.add(ModItems.JET_ENGINE);
            itemGroup.add(ModItems.THRUSTER);
            itemGroup.add(ModItems.PROPELLER);
            itemGroup.add(ModItems.FUEL_CELL);
            itemGroup.add(ModItems.EMPTY_FUEL_CELL);
           // itemGroup.add(ModItems.JETPACK_CHESTPLATE);
            // ...
        });
    }

}