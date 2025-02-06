package org.snekker.jetpack.network;


import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SetFuelPayload(int fuel) implements CustomPayload {
    public static final CustomPayload.Id<SetFuelPayload> ID = new CustomPayload.Id<>(Identifier.of("jetpack", "set_fuel_payload"));
    public static final PacketCodec<RegistryByteBuf, SetFuelPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, SetFuelPayload::fuel, SetFuelPayload::new);


    @Override

    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
