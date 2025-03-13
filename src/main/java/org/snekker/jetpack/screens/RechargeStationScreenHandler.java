package org.snekker.jetpack.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.world.World;

public class RechargeStationScreenHandler extends ScreenHandler {

    private final World world;
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public RechargeStationScreenHandler (int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreens.RECHARGE_STATION, syncId);

        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.getWorld();
        this.inventory = inventory;

        addSlot(new IngredientSlot(inventory, 0, 66, 17));
        addSlot(new EmptyFuelCellSlot( inventory, 1, 94, 53));
        addSlot(new FuelCellSlot(this, inventory, 2, 122, 53));
        addSlot(new FuelSlot(this, inventory, 3, 66, 53));
        addPlayerSlots(playerInventory, 8, 84);

        addProperties(propertyDelegate);
    }

    public boolean isFuel(ItemStack item) {
        return world.getFuelRegistry().isFuel(item);
    }

    public RechargeStationScreenHandler (int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(4));
    }

    @Override
    public ItemStack quickMove(PlayerEntity playerEntity, int slotIndex){
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
 