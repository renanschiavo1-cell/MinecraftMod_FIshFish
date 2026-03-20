package net.texugrosso.fishfish.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.texugrosso.fishfish.FishFish;
import net.texugrosso.fishfish.item.Moditems;

import java.util.function.Supplier;

public class ModBlocks {
    // 1. Cria o registro para os Blocos
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FishFish.MOD_ID);

    // 2. Cria o Bloco de Mana (Copiando a resistência e o som do Bloco de Ferro)
    public static final DeferredBlock<Block> MANA_BLOCK = registerBlock("mana_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    // Usando as propriedades da Mesa de Encantamentos como base física
    public static final DeferredBlock<Block> MANA_ALTAR = registerBlock("mana_altar",
            () -> new ManaAltarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)));

    // Método auxiliar: Registra o bloco e automaticamente cria o item dele para o inventário
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        Moditems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    // 3. Método para ligar isso no jogo
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}