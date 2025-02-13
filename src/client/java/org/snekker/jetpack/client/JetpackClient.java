package org.snekker.jetpack.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import org.snekker.jetpack.ModItems;
import org.snekker.jetpack.component.ModComponents;
import org.snekker.jetpack.network.SetFuelPayload;
import org.snekker.jetpack.sound.ModSounds;


public class JetpackClient implements ClientModInitializer {

   // private static ClientPlayerEntity player;
    //private static KeyBinding keyBinding;
    //private static PlayerEntity player1;
    private boolean canDash = true;
    private boolean canFly = false;

    @Override
    public void onInitializeClient() {

        JetpackKeys.registerKeybinds();
/*
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jetpack.spook", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_SPACE, // The keycode of the key
                "category.jetpack.test" // The translation key of the keybinding's category.
        ));

 */



        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null || client.world == null || client.player.isInCreativeMode() || client.player.isSpectator()) {
                return;
            }

            var jetpack = client.player.getEquippedStack(EquipmentSlot.CHEST);
            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);
            if (!client.player.isOnGround()) {

                if (!client.player.input.playerInput.jump()) {
                    canFly = true;
                }

                if (client.player.input.playerInput.sprint() && canDash && fuel >= 100) {
                    Vec3d forward = client.player.getRotationVector().multiply(1.0, 0, 1.0).normalize().multiply(2.0); // Change 1.0 to your desired speed
                    client.player.setVelocity(forward);
                    canDash = false;
                    client.player.sendMessage(Text.literal("Dash!"), false);
                    fuel -= 100;

                }

                if (client.player.input.playerInput.jump() && jetpack.isOf(ModItems.JETPACK) && !jetpack.isEmpty() && fuel > 0 && canFly) {

                    Vec3d currentVelocity = client.player.getVelocity();
                    Vec3d currentDirection = client.player.getRotationVecClient();

                    if (client.player.input.playerInput.sneak()){
                        client.player.setVelocity(currentVelocity.add(0, 0 - currentVelocity.getY(), 0));
                    } else {
                        if (currentVelocity.y < 0) {
                            client.player.setVelocity(currentVelocity.add(0, 0.25, 0));
                        } else if (currentVelocity.y < 0.3) {
                            client.player.setVelocity(currentVelocity.add(0, 0.1, 0));
                        }
                    }
                    var vec = client.player.getRotationVector();
                    var x = client.player.getX() - vec.getX();
                    var y = client.player.getY() + 0.6;
                    var z = client.player.getZ() - vec.getZ();

                    double offset = 0.1;

                    if (client.player.isInFluid()) {
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset, y, z, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z + offset, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset, y, z + offset + offset, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset, y, z + offset, 0, -0.08, 0);
                        client.world.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + offset + offset, y, z, 0, -0.08, 0);
                        client.world.playSound(client.player, x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.PLAYERS);

                    } else if (client.player.isInvisible()) {
                        client.world.addParticle(ParticleTypes.END_ROD, x, y, z, 0, -0.02, 0);

                    } else {

                        client.world.addParticle(ParticleTypes.CLOUD, x, y, z, 0, -0.1, 0);
                        client.world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, -0.06, 0);
                        client.world.playSound(client.player, x, y, z, ModSounds.JETPACK_SOUND, SoundCategory.PLAYERS);
                    }
                    fuel -= 1;
                    jetpack.set(ModComponents.JETPACK_FUEL_COMPONENT, fuel);
                    ClientPlayNetworking.send(new SetFuelPayload(fuel));


                }


            } else if (client.player.isOnGround() || client.player.isClimbing()){
                canFly = false;
                canDash = true;
            }


        });



        HudRenderCallback.EVENT.register((context, renderTickCounter) -> {

            var client = MinecraftClient.getInstance();
            var jetpack = client.player.getEquippedStack(EquipmentSlot.CHEST);
            var fuel = jetpack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0);


                    /*Matrix4f transformationMatrix = context.getMatrices().peek().getPositionMatrix();
                    Tessellator tessellator = Tessellator.getInstance();

                    BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

                    buffer.vertex(transformationMatrix, 20, 20, 5).color(0xFF414141);
                    buffer.vertex(transformationMatrix, 5, 40, 5).color(0xFF000000);
                    buffer.vertex(transformationMatrix, 35, 40, 5).color(0xFF000000);
                    buffer.vertex(transformationMatrix, 20, 60, 5).color(0xFF414141);

                    RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                    BufferRenderer.drawWithGlobalProgram(buffer.end());*/
            if (jetpack.isOf(ModItems.JETPACK)) {

                int rectangleX = 10;
                int rectangleY = 10;
                int rectangleWidth = 10;
                int rectangleHeight;

                if (fuel == 0) {
                    rectangleHeight = 0;
                } else {
                    rectangleHeight = Math.min(Math.max(Math.ceilDiv(fuel, 40), 1), 50);
                }

                context.fill(8, 8, 22, 62, 0xFF373a3e);
                context.fill(rectangleX, rectangleY + 50 - rectangleHeight, rectangleX + rectangleWidth, rectangleY + 50, 0xFF3cb0da);
            }
        });
    }

}
