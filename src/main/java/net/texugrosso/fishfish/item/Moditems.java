package net.texugrosso.fishfish.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.texugrosso.fishfish.FishFish;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.texugrosso.fishfish.entity.ModEntities;
import net.minecraft.world.item.ArmorItem;

public class Moditems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FishFish.MOD_ID);

    public static final DeferredItem<Item> STARFISH = ITEMS.register("starfish",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MANA_SCALE = ITEMS.register("mana_scale",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<SwordItem> MANA_SWORD = ITEMS.register("mana_sword",
            () -> new ManaSwordItem(Tiers.GOLD, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.GOLD, 3, -2.4f))));

    public static final DeferredItem<Item> HARDENED_MANA_SCALE = ITEMS.register("hardened_mana_scale",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CRYSTALLIZED_IRON = ITEMS.register("crystallized_iron",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CRYSTALLIZED_GOLD = ITEMS.register("crystallized_gold",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<PickaxeItem> MANA_PICKAXE = ITEMS.register("mana_pickaxe",
            () -> new ManaPickaxeItem(Tiers.DIAMOND, new Item.Properties().attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1, -2.8f))));

    public static final DeferredItem<Item> MANA_FISH_SPAWN_EGG = ITEMS.register("mana_fish_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MANA_FISH, 0x00FFFF, 0x800080, new Item.Properties()));

    public static final DeferredItem<ArmorItem> MANA_HELMET = ITEMS.register("mana_helmet",
            () -> new ArmorItem(ModArmorMaterials.MANA, ArmorItem.Type.HELMET, new Item.Properties().durability(400)));

    public static final DeferredItem<ArmorItem> MANA_CHESTPLATE = ITEMS.register("mana_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MANA, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(600)));

    public static final DeferredItem<ArmorItem> MANA_LEGGINGS = ITEMS.register("mana_leggings",
            () -> new ArmorItem(ModArmorMaterials.MANA, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(550)));

    public static final DeferredItem<ArmorItem> MANA_BOOTS = ITEMS.register("mana_boots",
            () -> new ArmorItem(ModArmorMaterials.MANA, ArmorItem.Type.BOOTS, new Item.Properties().durability(350)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
