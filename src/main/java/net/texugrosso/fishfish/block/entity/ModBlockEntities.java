package net.texugrosso.fishfish.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.texugrosso.fishfish.FishFish;
import net.texugrosso.fishfish.block.ModBlocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    // 1. A gaveta de Block Entities
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FishFish.MOD_ID);

    // 2. Conectando o nosso Cérebro (ManaAltarBlockEntity) com a casca física (MANA_ALTAR)
    public static final Supplier<BlockEntityType<ManaAltarBlockEntity>> MANA_ALTAR_BE =
            BLOCK_ENTITIES.register("mana_altar_be", () ->
                    BlockEntityType.Builder.of(ManaAltarBlockEntity::new, ModBlocks.MANA_ALTAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}