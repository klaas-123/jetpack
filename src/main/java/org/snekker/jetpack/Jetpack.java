package org.snekker.jetpack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class Jetpack implements ModInitializer {
    public static final String MOD_ID = "jetpack";

    @Override
    public void onInitialize() {
        ModItems.initialize();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(ModItems.JETPACK));
    }

}