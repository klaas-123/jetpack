package org.snekker.jetpack.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.snekker.jetpack.component.ModComponents;
import net.minecraft.item.equipment.EquipmentAsset;


import java.util.EnumMap;
import java.util.List;

public class JetpackItem extends ArmorItem {

    public static final Integer FUEL = 2000;

    static RegistryKey<EquipmentAsset> JETPACK_EQUIPMENT_ASSET_KEY = EquipmentAssetKeys.register("jetpack");
    static ArmorMaterial JETPACK_MAT = new ArmorMaterial(15, Util.make(new EnumMap(EquipmentType.class), (map) -> {
        map.put(EquipmentType.CHESTPLATE, 0);
    }), 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ItemTags.REPAIRS_GOLD_ARMOR, JETPACK_EQUIPMENT_ASSET_KEY);

    public JetpackItem(Settings settings) {
        super(JETPACK_MAT, EquipmentType.CHESTPLATE, settings.component(ModComponents.JETPACK_FUEL_COMPONENT, FUEL));

    }



    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.literal(stack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0) + " " + "fuel").formatted(Formatting.GOLD));
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return (int) Math.ceil(stack.getOrDefault(ModComponents.JETPACK_FUEL_COMPONENT, 0) * 13.0F / FUEL);

    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xff0001;
    }

}
