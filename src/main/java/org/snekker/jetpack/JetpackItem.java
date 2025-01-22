package org.snekker.jetpack;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class JetpackItem extends ArmorItem {
    public JetpackItem(Settings settings) {
        super(ArmorMaterials.GOLD, EquipmentType.CHESTPLATE, settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            return ActionResult.PASS;
        }

        /*var abilities = player.getAbilities();
        if (!abilities.allowFlying) {
            abilities.allowFlying = true;
        }
        abilities.flying = !abilities.flying;*/
        






        return ActionResult.SUCCESS;
    }
}
