package org.snekker.jetpack.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.screens.slots.*;

public class RechargeStationScreenHandler extends ScreenHandler {

    private final World world;
    private final PropertyDelegate propertyDelegate;

    public RechargeStationScreenHandler (int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreens.RECHARGE_STATION, syncId);

        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.getWorld();

        addSlot(new IngredientSlot(inventory, 0, 66, 17));
        addSlot(new EmptyFuelCellSlot( inventory, 1, 94, 53));
        addSlot(new FuelCellSlot(this, inventory, 2, 122, 53));
        addSlot(new FuelSlot(this, inventory, 3, 66, 53));
        addSlot(new FuelCellSlot(this, inventory, 4, 191, 41));
        addSlot(new JetpackSlot(inventory, 5, 191, 23));

        addPlayerSlots(playerInventory, 8, 84);

        addProperties(propertyDelegate);
    }

    public boolean isFuel(ItemStack item) {
        return world.getFuelRegistry().isFuel(item);
    }

    public boolean isCharging() {
        return getChargeProgress() > 0;
    }

    public float getChargeProgress() {
        return ((float)propertyDelegate.get(1) - propertyDelegate.get(0)) / propertyDelegate.get(1);
    }

    public RechargeStationScreenHandler (int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(4));
    }

    @Override
    public ItemStack quickMove(PlayerEntity playerEntity, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(slotIndex);
        var ingredientSlot = (IngredientSlot) slots.get(0);
        var fuelSlot = (FuelSlot) slots.get(3);
        var emptyCellSlot = (EmptyFuelCellSlot) slots.get(1);

        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            if (slot.getStack().isOf(ModItems.FUEL_CELL)) {
                if (slotIndex == 0) {
                    // going from jetpack slot to inventory
                    if (!insertItem(slotStack, 2, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                    slot.markDirty();
                } else if (!ingredientSlot.hasStack()) {
                    // going from inventory to jetpack slot
                    if (!insertItem(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (slotIndex == 1) {
                // going from fuel slot to inventory
                if (!insertItem(slotStack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
                slot.markDirty();
            } else if (isFuel(slot.getStack()) && !fuelSlot.hasStack()) {
                // going from inventory to fuel slot
                if (!insertItem(slotStack, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
 