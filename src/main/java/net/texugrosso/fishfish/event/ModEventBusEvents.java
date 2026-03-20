package net.texugrosso.fishfish.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.texugrosso.fishfish.entity.ModEntities;
import net.texugrosso.fishfish.entity.ManaFishEntity;
import net.texugrosso.fishfish.entity.client.ManaFishModel;
import net.texugrosso.fishfish.entity.client.ManaFishRenderer;

// Isso avisa o NeoForge para ler essa classe automaticamente quando o jogo abrir!
@EventBusSubscriber(modid = "fishfish", bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    // 1. Aplica a vida (corações) e velocidade ao cérebro do peixe
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MANA_FISH.get(), ManaFishEntity.createAttributes().build());
    }

    // 2. Conecta o Modelo 3D com o Renderizador
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.MANA_FISH.get(), ManaFishRenderer::new);
    }

    // 3. Salva a geometria (esqueleto) na placa de vídeo do jogador
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ManaFishModel.LAYER_LOCATION, ManaFishModel::createBodyLayer);
    }
}