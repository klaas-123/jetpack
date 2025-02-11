package org.snekker.jetpack.client.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.snekker.jetpack.client.JetpackFeatureRenderer;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(at=@At("TAIL"), method="<init>")
    public void init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo info) {
        var invoker = (ILivingEntityRendererInvoker)this;
        var renderer = (PlayerEntityRenderer)(Object)this;
        invoker.addFeature_mixin(new JetpackFeatureRenderer(renderer, ctx.getEntityModels()));
    }
}