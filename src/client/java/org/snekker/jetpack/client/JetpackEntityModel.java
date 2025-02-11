package org.snekker.jetpack.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import org.snekker.jetpack.Jetpack;


public class JetpackEntityModel<T extends PlayerEntityRenderState> extends BipedEntityModel<T> {
    public JetpackEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        var modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);

        var root = modelData.getRoot();
        var head = root.addChild("head");
        head.addChild("hat");
        var body = root.addChild("body");
        root.addChild("left_arm");
        root.addChild("right_arm");
        root.addChild("left_leg");
        root.addChild("right_leg");

        var canisterBuilder = ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-4.0F, 0F, -3.0F, 2.5F, 6.5F, 2.5F, new Dilation(1.0F));
        body.addChild("canister_left", canisterBuilder, ModelTransform.pivot(-0.5F, 0, 6.0F));
        body.addChild("canister_right", canisterBuilder, ModelTransform.pivot(6.0F, 0, 6.0F));
        var canisterExtendedBuilder = ModelPartBuilder.create()
                .uv(10, 0)
                .cuboid(-3.5F, -0.5F, -3.0F, 2.0F, 0.5F, 2.0F, new Dilation(0.75F));
        body.addChild("canister_extended_left", canisterExtendedBuilder, ModelTransform.pivot(-0.75F, 7.5F, 6.25F));
        body.addChild("canister_extended_right", canisterExtendedBuilder, ModelTransform.pivot(5.75F, 7.5F, 6.25F));
        var centerBuilder = ModelPartBuilder.create()
                .uv(0, 21)
                .cuboid(-2.5F, 0.5F, -3.5F, 1.0F, 2.0F, 1.5F, new Dilation(0.66F));
        body.addChild("center", centerBuilder, ModelTransform.pivot(2F, 1F, 6.5F));
        var thrusterBuilder = ModelPartBuilder.create()
                .uv(10, 13)
                .cuboid(-3.0F, 0.5F, -3.0F, 1.5F, 1F, 1.5F, new Dilation(0.25F));
        body.addChild("thruster_left", thrusterBuilder, ModelTransform.pivot(-1.0F, 8.0F, 6.5F));
        body.addChild("thruster_right", thrusterBuilder, ModelTransform.pivot(5.5F, 8.0F, 6.5F));
        var thrusterEndBuilder = ModelPartBuilder.create()
                .uv(13, 14)
                .cuboid(-2.5F, 1.75F, -2.5F, 0.5F, 0.5F, 0.5F, new Dilation(0.125F));
        body.addChild("thruster_end_left", thrusterEndBuilder, ModelTransform.pivot(-1.0F, 8.0F, 6.5F));
        body.addChild("thruster_end_right", thrusterEndBuilder, ModelTransform.pivot(5.5F, 8.0F, 6.5F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    public static EntityModelLayer JETPACK = new EntityModelLayer(Identifier.of(Jetpack.MOD_ID), "main");

}
