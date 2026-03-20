package net.texugrosso.fishfish.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.texugrosso.fishfish.FishFish;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    // 1. A gaveta oficial de Materiais de Armadura do nosso mod
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, FishFish.MOD_ID);

    // 2. Criando o material "MANA"
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MANA = ARMOR_MATERIALS.register("mana", () -> new ArmorMaterial(
            // Defesa de cada parte (Botas, Calça, Peitoral, Capacete) - Usamos os status do Diamante!
            net.minecraft.Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.HELMET, 3);
            }),
            // Nível de Encantamento (20 é bem alto, pega encantamentos bons fácil)
            20,
            // Som quando o jogador veste a armadura
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            // Item usado para consertar na bigorna (Sua Escama Endurecida!)
            () -> Ingredient.of(Moditems.HARDENED_MANA_SCALE.get()),
            // Nome da textura 3D que vai no corpo ("mana")
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(FishFish.MOD_ID, "mana"))),
            // Toughness (Resistência extra igual a do diamante)
            2.0f,
            // Resistência a Repulsão (Knockback)
            0.0f
    ));

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}