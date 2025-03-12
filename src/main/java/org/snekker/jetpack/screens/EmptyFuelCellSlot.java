package org.snekker.jetpack.screens;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.snekker.jetpack.ModItems;

public class EmptyFuelCellSlot extends Slot {

    public EmptyFuelCellSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

}
