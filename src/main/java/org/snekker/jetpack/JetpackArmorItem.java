package org.snekker.jetpack;


import net.minecraft.item.ArmorItem;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;

public class JetpackArmorItem extends ArmorItem {
    public JetpackArmorItem(Settings settings) {
        super(ArmorMaterials.ARMADILLO_SCUTE, EquipmentType.CHESTPLATE, settings);

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
}