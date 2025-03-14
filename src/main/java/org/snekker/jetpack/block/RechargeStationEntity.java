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
    private int fuelLeft = 0;
    private int fuelMax = 0;


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

    public void setIngredientSlotStack(ItemStack stack) {
        inventory.setStack(0, stack);
    }

    public ItemStack getEmptyCellSlotStack() {
        return inventory.getStack(1);
    }

    public void setEmptyCellSlotStack(ItemStack stack) {
        inventory.setStack(1, stack);
    }

    public ItemStack getCellSlotStack() {
        return inventory.getStack(2);
    }

    public void setCellSlotStack(ItemStack stack) {
        inventory.setStack(2, stack);
    }

    public ItemStack getFuelSlotStack() {
        return inventory.getStack(3);
    }

    public void setFuelSlotStack(ItemStack stack) {
        inventory.setStack(3, stack);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.jetpack.recharge_station.title");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RechargeStationScreenHandler(syncId, playerInventory, inventory, propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        var fuelStack = getFuelSlotStack();
        var ingredientStack = getIngredientSlotStack();
        var cellStack = getCellSlotStack();
        var emptyCellStack = getEmptyCellSlotStack();
        var hasFuel = !fuelStack.isEmpty();
        var hasIngredient = !ingredientStack.isEmpty();
        var hasCell = !cellStack.isEmpty();
        var hasEmptyCell = !emptyCellStack.isEmpty();


        if (hasEmptyCell && hasIngredient) {
            if (!hasFuel) {
                fuelLeft = fuelMax = 0;
                return;
            }

            if (fuelLeft == 0) {
                if (hasFuel) {
                    var replaceWithBucket = fuelStack.getItem() instanceof BucketItem;
                    fuelLeft = fuelMax = world.getFuelRegistry().getFuelTicks(fuelStack);
                    if (fuelLeft > 0) {
                        fuelStack.decrement(1);
                        if (replaceWithBucket) {
                            setFuelSlotStack(new ItemStack(Items.BUCKET));
                        }
                    }
                } else {
                    fuelMax = 0;
                }
            }

            if (fuelLeft > 0) {
                fuelLeft = Math.clamp(fuelLeft - 16 , 0, fuelLeft);
                if (fuelLeft == 0) {
                    if (!hasCell) {
                        setCellSlotStack(new ItemStack(ModItems.FUEL_CELL));
                    } else if (hasCell) {
                        cellStack.increment(1);
                    }
                    emptyCellStack.decrement(1);
                    ingredientStack.decrement(1);
                }
            }


            /*

            emptyCellStack.decrement(1);
            ingredientStack.decrement(1);
            fuelStack.decrement(1);
            if (!hasCell) {
                setCellSlotStack(new ItemStack(ModItems.FUEL_CELL));
            } else if (hasCell) {
                cellStack.increment(1);
            }

             */

        }

    }

    @Override
    public void onInventoryChanged(Inventory sender) {}
}
