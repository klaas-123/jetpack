package org.snekker.jetpack.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.snekker.jetpack.Jetpack;
import org.snekker.jetpack.screens.RechargeStationScreenHandler;

public class RechargeStationEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public final SimpleInventory inventory;
    public final PropertyDelegate propertyDelegate;

    public RechargeStationEntity(BlockPos pos, BlockState state) {
        super(JetpackBlockEntityTypes.RECHARGE_STATION_ENTITY, pos, state);

        inventory = new SimpleInventory(2);

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

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.jetpack.recharge_station.title");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RechargeStationScreenHandler(syncId, playerInventory, inventory, propertyDelegate);
    }
}
