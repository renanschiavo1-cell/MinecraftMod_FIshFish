package net.texugrosso.fishfish.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModEntities {
    // Cria a nossa lista oficial de Entidades (Mobs)
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, "fishfish");

    // Registra o nosso Mana Fish!
    public static final Supplier<EntityType<ManaFishEntity>> MANA_FISH =
            ENTITY_TYPES.register("mana_fish",
                    () -> EntityType.Builder.of(ManaFishEntity::new, MobCategory.WATER_CREATURE)
                            .sized(0.5f, 0.4f) // Esse é o tamanho da "caixa de colisão" dele!
                            .build("mana_fish"));
}