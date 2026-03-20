package net.texugrosso.fishfish.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.texugrosso.fishfish.item.Moditems;

public class ManaAltarBlockEntity extends BlockEntity {

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    // --- VARIÁVEIS DO TIMER ---
    private int progress = 0;
    private int maxProgress = 100; // 100 ticks = 5 segundos de espera!

    // O "mensageiro" que envia os números do servidor para a interface do jogador
    public final ContainerData data;

    public ManaAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MANA_ALTAR_BE.get(), pos, state);

        // Configura o mensageiro para rastrear o Progresso (Index 0) e o Progresso Máximo (Index 1)
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return pIndex == 0 ? ManaAltarBlockEntity.this.progress : ManaAltarBlockEntity.this.maxProgress;
            }
            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex == 0) ManaAltarBlockEntity.this.progress = pValue;
                else ManaAltarBlockEntity.this.maxProgress = pValue;
            }
            @Override
            public int getCount() {
                return 2; // Quantidade de variáveis sendo rastreadas
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("mana_altar.progress", progress); // Salva o timer no mundo
        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("mana_altar.progress"); // Carrega o timer
    }

    // A nossa fábrica com tempo de espera!
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) return;

        // Se tem os itens certos para fabricar...
        if (hasRecipe()) {
            progress++; // Aumenta o tempo a cada tick
            setChanged(pLevel, pPos, pState);

            // Quando chegar em 100 (5 segundos), ele cria o item!
            if (progress >= maxProgress) {
                craftItem();
                resetProgress();

                // --- TOCA UM SOM MÁGICO! ---
                pLevel.playSound(null, pPos, net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_CHIME, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            // Se tirar os itens da máquina antes da hora, o progresso zera
            resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    // --- O LIVRO DE RECEITAS DA NOSSA MÁQUINA ---
    // Ele olha o item de entrada e devolve o que deve sair
    private ItemStack getRecipeResult(ItemStack input) {
        if (input.getItem() == Moditems.MANA_SCALE.get()) {
            return new ItemStack(Moditems.HARDENED_MANA_SCALE.get(), 1);
        }
        // Receita 2: Ferro Cristalizado -> Barra de Ferro
        else if (input.getItem() == Moditems.CRYSTALLIZED_IRON.get()) {
            return new ItemStack(net.minecraft.world.item.Items.IRON_INGOT, 1);
        }
        // Receita 3: Ouro Cristalizado -> Barra de Ouro
        else if (input.getItem() == Moditems.CRYSTALLIZED_GOLD.get()) {
            return new ItemStack(net.minecraft.world.item.Items.GOLD_INGOT, 1);
        }

        return ItemStack.EMPTY; // Se colocar terra ou qualquer outra coisa, não faz nada
    }

    // Verifica se pode fabricar
    private boolean hasRecipe() {
        ItemStack inputSlot = itemHandler.getStackInSlot(0);
        ItemStack outputSlot = itemHandler.getStackInSlot(1);

        // Pergunta pro Livro de Receitas qual é o resultado do item que está na entrada
        ItemStack result = getRecipeResult(inputSlot);

        // Se o resultado for vazio, é porque o item não tem receita
        if (result.isEmpty()) {
            return false;
        }

        // Verifica se a saída está vazia OU se tem o mesmo item da receita e ainda cabe mais
        boolean canInsert = outputSlot.isEmpty() ||
                (outputSlot.getItem() == result.getItem() && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize());

        return canInsert;
    }

    // Faz a mágica acontecer de fato
    private void craftItem() {
        ItemStack inputSlot = itemHandler.getStackInSlot(0);
        ItemStack result = getRecipeResult(inputSlot); // Pega o resultado de novo

        if (!result.isEmpty()) {
            itemHandler.extractItem(0, 1, false); // Tira 1 da entrada

            // Usamos .copy() aqui para evitar bugs de referência de memória no Java!
            itemHandler.insertItem(1, result.copy(), false); // Coloca o resultado na saída
        }
    }
}