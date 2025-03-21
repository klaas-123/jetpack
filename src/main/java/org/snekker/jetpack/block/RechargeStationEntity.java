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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.item.JetpackItem;
import org.snekker.jetpack.screens.RechargeStationScreenHandler;

public class RechargeStationEntity extends BlockEntity implements NamedScreenHandlerFactory, InventoryChangedListener {
    public final SimpleInventory inventory;
    public final PropertyDelegate propertyDelegate;
    private int fuelLeft = 0;
    private int fuelMax = 0;


    public RechargeStationEntity(BlockPos pos, BlockState state) {
        super(JetpackBlockEntityTypes.RECHARGE_STATION_ENTITY, pos, state);

        inventory = new SimpleInventory(6);
        inventory.addListener(this);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RechargeStationEntity.this.fuelLeft;
                    case 1 -> RechargeStationEntity.this.fuelMax;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: RechargeStationEntity.this.fuelLeft = value; break;
                    case 1: RechargeStationEntity.this.fuelMax = value; break;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };


    }

    public ItemStack getIngredientSlotStack() {
        return inventory.getStack(0);
    }

    public ItemStack getJetpackSlotStack() {
        return inventory.getStack(5);
    }

    public ItemStack getEmptyCellSlotStack() {
        return inventory.getStack(1);
    }

    public ItemStack getCellSlotStack() {
        return inventory.getStack(2);
    }

    public ItemStack getFuelCellSlotStack() {
        return inventory.getStack(4);
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

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);

        var stack = inventory.getStack(0);
        if (!stack.isEmpty()) {
            nbt.put("ingredient_stack", stack.toNbt(registries));
        }
        stack = inventory.getStack(1);
        if (!stack.isEmpty()) {
            nbt.put("empty_fuel_cell_stack", stack.toNbt(registries));
        }

        stack = inventory.getStack(2);
        if (!stack.isEmpty()) {
            nbt.put("fuel_cell_stack", stack.toNbt(registries));
        }

        stack = inventory.getStack(3);
        if (!stack.isEmpty()) {
            nbt.put("fuel_stack", stack.toNbt(registries));
        }

        stack = inventory.getStack(4);
        if (!stack.isEmpty()) {
            nbt.put("fuel_cell_stack2", stack.toNbt(registries));
        }

        stack = inventory.getStack(5);
        if (!stack.isEmpty()) {
            nbt.put("jetpack_stack", stack.toNbt(registries));
        }

        nbt.putInt("fuel_left", fuelLeft);
        nbt.putInt("fuel_max", fuelMax);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("ingredient_stack")) {
            var stack = ItemStack.fromNbt(registries, nbt.get("ingredient_stack"));
            stack.ifPresent(s -> inventory.setStack(0, s));
        }
        if (nbt.contains("empty_fuel_cell_stack")) {
            var stack = ItemStack.fromNbt(registries, nbt.get("empty_fuel_cell_stack"));
            stack.ifPresent(s -> inventory.setStack(1, s));
        }

        if (nbt.contains("fuel_cell_stack")) {
            var stack = ItemStack.fromNbt(registries, nbt.get("fuel_cell_stack"));
            stack.ifPresent(s -> inventory.setStack(2, s));
        }

        if (nbt.contains("fuel_stack")) {
            var stack = ItemStack.fromNbt(registries, nbt.get("fuel_stack"));
            stack.ifPresent(s -> inventory.setStack(3, s));
        }

        if (nbt.contains("fuel_cell_stack2")) {
            var stack = ItemStack.fromNbt(registries, nbt.get("fuel_cell_stack2"));
            stack.ifPresent(s -> inventory.setStack(4, s));
        }

        if (nbt.contains("jetpack_stack")) {
            var stack = ItemStack.fromNbt(registries, nbt.get("jetpack_stack"));
            stack.ifPresent(s -> inventory.setStack(5, s));
        }

        fuelLeft = nbt.getInt("fuel_left");
        fuelMax = nbt.getInt("fuel_max");
    }

    public void tick(World world, BlockPos ignoredPos, BlockState ignoredState) {
        var fuelStack = getFuelSlotStack();
        var ingredientStack = getIngredientSlotStack();
        var cellStack = getCellSlotStack();
        var emptyCellStack = getEmptyCellSlotStack();
        var hasFuel = !fuelStack.isEmpty();
        var hasIngredient = !ingredientStack.isEmpty();
        var hasCell = !cellStack.isEmpty();
        var hasEmptyCell = !emptyCellStack.isEmpty();
        var jetpackStack = getJetpackSlotStack();
        var hasJetpack = !jetpackStack.isEmpty();
        var fuelCellStack = getCellSlotStack();
        var hasFuelCell = !fuelCellStack.isEmpty();
        var fuel = jetpackStack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);


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
    public void onInventoryChanged(Inventory sender) {
        markDirty();
    }
}
