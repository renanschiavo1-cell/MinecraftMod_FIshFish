package net.texugrosso.fishfish.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.texugrosso.fishfish.FishFish;
import net.texugrosso.fishfish.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    // 1. Cria o registro para Abas Criativas
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FishFish.MOD_ID);

    // 2. Constrói a nossa aba específica
    public static final Supplier<CreativeModeTab> FISHFISH_TAB = CREATIVE_MODE_TABS.register("fishfish_tab",
            () -> CreativeModeTab.builder()
                    // Define o ícone da aba (Estrela do Mar)
                    .icon(() -> new ItemStack(Moditems.STARFISH.get()))
                    // Define o nome interno da aba para tradução
                    .title(Component.translatable("creativetab.fishfish"))
                    // Adiciona os itens dentro da aba
                    .displayItems((parameters, output) -> {
                        output.accept(Moditems.STARFISH.get());
                        output.accept(Moditems.MANA_SCALE.get());
                        output.accept(Moditems.MANA_SWORD.get());
                        output.accept(ModBlocks.MANA_BLOCK.get());
                        output.accept(ModBlocks.MANA_ALTAR.get());
                        output.accept(Moditems.HARDENED_MANA_SCALE.get());
                        output.accept(Moditems.MANA_PICKAXE.get());
                        output.accept(Moditems.CRYSTALLIZED_IRON.get());
                        output.accept(Moditems.CRYSTALLIZED_GOLD.get());
                        output.accept(Moditems.MANA_FISH_SPAWN_EGG.get());
                        output.accept(Moditems.MANA_HELMET.get());
                        output.accept(Moditems.MANA_CHESTPLATE.get());
                        output.accept(Moditems.MANA_LEGGINGS.get());
                        output.accept(Moditems.MANA_BOOTS.get());
                    })
                    .build());

    // 3. Método para registrar tudo no jogo
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
