package org.snekker.jetpack.screens.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.screens.RechargeStationScreenHandler;


public class FuelCellSlot extends Slot {
    public FuelCellSlot(RechargeStationScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(ModItems.FUEL_CELL);
    }
}
