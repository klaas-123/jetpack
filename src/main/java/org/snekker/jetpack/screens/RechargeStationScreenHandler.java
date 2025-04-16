package org.snekker.jetpack.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.item.JetpackItem;
import org.snekker.jetpack.screens.slots.*;

public class RechargeStationScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final World world;
    private final PropertyDelegate propertyDelegate;

    public RechargeStationScreenHandler (int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreens.RECHARGE_STATION, syncId);
        this.inventory = inventory;
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

    public Text getJetpackFuelStat() {
        return Text.literal("" + propertyDelegate.get(2)).withColor(0xFFFFFFFF)
                .append(Text.literal("/").withColor(0xFFFFFFFF))
                .append(Text.literal("" + propertyDelegate.get(3)));
    }

    public RechargeStationScreenHandler (int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(4));
    }

    @Override
    public ItemStack quickMove(PlayerEntity playerEntity, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (slotIndex < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Moving from player inventory → container inventory
                if (originalStack.getItem() == ModItems.FUEL_CELL) {
                    // Only try to insert into slot 4
                    Slot targetSlot = this.slots.get(4);
                    if (!targetSlot.canInsert(originalStack)) {
                        return ItemStack.EMPTY;
                    }

                    if (!this.insertItem(originalStack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Handle all other items normally (e.g., try to insert anywhere in container inventory)
                    if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
 