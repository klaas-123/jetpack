package org.snekker.jetpack.block;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RechargeStation extends BlockWithEntity implements BlockEntityTicker<RechargeStationEntity> {
    public RechargeStation(Settings settings) {
        super(settings);
    }

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(RechargeStation::new);
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RechargeStationEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type == JetpackBlockEntityTypes.RECHARGE_STATION_ENTITY) {
            return (BlockEntityTicker<T>) this;
        } else {
            return null;
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return super.onUse(state, world, pos, player, hit);
        }

        if (world.getBlockEntity(pos) instanceof RechargeStationEntity rechargeStationEntity) {
            player.openHandledScreen(rechargeStationEntity);

        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        return super.getDroppedStacks(state, builder);
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, RechargeStationEntity blockEntity) {
        blockEntity.tick(world, pos, state);

    }


}
