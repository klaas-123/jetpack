package org.snekker.jetpack.screens;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.snekker.jetpack.ModItems;


public class FuelCellSlot extends Slot {
    private final RechargeStationScreenHandler handler;
    public FuelCellSlot(RechargeStationScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(ModItems.FUEL_CELL);
    }
}
