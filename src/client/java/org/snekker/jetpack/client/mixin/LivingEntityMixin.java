package org.snekker.jetpack.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.snekker.jetpack.item.JetpackItem;
import org.snekker.jetpack.component.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("RETURN"), method = "onEquipStack")
    public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo callbackInfo) {
        // detect stack heeft jetpack?

        var entity = (LivingEntity) (Object) this;
        if (entity instanceof ClientPlayerEntity playerEntity) {

            if (slot.isArmorSlot()) {

                var abilities = playerEntity.getAbilities();
                if (oldStack.getItem() instanceof JetpackItem) {

                }
                if (newStack.getItem() instanceof JetpackItem) {
                     
                }
            }

        }
    }
}



