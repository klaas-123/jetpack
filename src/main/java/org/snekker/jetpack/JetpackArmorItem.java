package org.snekker.jetpack;


import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.snekker.jetpack.component.ModComponents;

import java.util.List;
import java.util.Stack;

public class JetpackArmorItem extends ArmorItem {
    public JetpackArmorItem(Settings settings) {
        super(ArmorMaterials.ARMADILLO_SCUTE, EquipmentType.CHESTPLATE, settings.component(ModComponents.JETPACK_FUEL_COMPONENT, 2000));

    /*
    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            return ActionResult.PASS;
        }

        var abilities = player.getAbilities();
        if (!abilities.allowFlying) {
            abilities.allowFlying = true;
        }
        abilities.flying = !abilities.flying;







        return ActionResult.SUCCESS;*/
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.literal(stack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0) + " " + "Fuel"));
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return (int) Math.ceil(stack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0) * 13.0F / 2000.0F);

    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xff0001;
    }
}