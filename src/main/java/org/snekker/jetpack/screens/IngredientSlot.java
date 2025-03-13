package org.snekker.jetpack.screens;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MinecartItem;
import net.minecraft.screen.slot.Slot;

public class IngredientSlot extends Slot {

    public IngredientSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(Items.EMERALD) || stack.isOf(Items.ENDER_PEARL) || stack.isOf(Items.ECHO_SHARD);
    }
}
