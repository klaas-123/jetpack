package org.snekker.jetpack.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.item.FuelItem;
import org.snekker.jetpack.item.JetpackItem;
import org.snekker.jetpack.screens.IngredientSlot;
import org.snekker.jetpack.screens.RechargeStationScreenHandler;

public class RechargeStationEntity extends BlockEntity implements NamedScreenHandlerFactory, InventoryChangedListener {
    public final SimpleInventory inventory;
    public final PropertyDelegate propertyDelegate;


    public RechargeStationEntity(BlockPos pos, BlockState state) {
        super(JetpackBlockEntityTypes.RECHARGE_STATION_ENTITY, pos, state);

        inventory = new SimpleInventory(4);
        inventory.addListener(this);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 0;
            }
        };


    }

    public ItemStack getIngredientSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack setIngredientSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack getEmptyCellSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack setEmptyCellSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack getCellSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack setCellSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack getFuelSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack setFuelSlotStack() {
        return inventory.getStack(0);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.jetpack.recharge_station.title");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RechargeStationScreenHandler(syncId, playerInventory, inventory, propertyDelegate);
    }
}
