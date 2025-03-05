package org.snekker.jetpack.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.snekker.jetpack.Jetpack;

public class JetpackBlockEntityTypes {
    public static final BlockEntityType<RechargeStationEntity> RECHARGE_STATION_ENTITY = register(
            "recharge_station",
            FabricBlockEntityTypeBuilder.<RechargeStationEntity>create(RechargeStationEntity::new, ModBlocks.RECHARGE_STATION).build());

    public static <T extends BlockEntityType<?>> T register(String identifier, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Jetpack.id(identifier), blockEntityType);
    }

    public static void registerBlockEntityTypes() {
    }
}
