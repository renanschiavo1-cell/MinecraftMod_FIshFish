package net.texugrosso.fishfish.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.texugrosso.fishfish.block.entity.ManaAltarBlockEntity;
import net.texugrosso.fishfish.screen.ManaAltarMenu;
import org.jetbrains.annotations.Nullable;

// Implementa EntityBlock para avisar o jogo que este bloco tem um cérebro
public class ManaAltarBlock extends Block implements EntityBlock {

    public ManaAltarBlock(Properties properties) {
        super(properties);
    }

    // Cria o cérebro assim que o bloco for colocado no chão
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ManaAltarBlockEntity(pos, state);
    }

    // O relógio do bloco!
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) return null;

        return (level, pos, state, blockEntity) -> {
            if (blockEntity instanceof ManaAltarBlockEntity altar) {
                altar.tick(level, pos, state);
            }
        };
    }

    // Método disparado quando o jogador clica com o botão direito no bloco
    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof ManaAltarBlockEntity) {
                pPlayer.openMenu(new SimpleMenuProvider(
                        (id, inv, player) -> new ManaAltarMenu(id, inv, entity),
                        Component.translatable("block.fishfish.mana_altar")), pPos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    // --- NOVO: FAZ O BLOCO CUSPIR OS ITENS QUANDO QUEBRADO ---
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        // Verifica se o bloco foi realmente quebrado (e não apenas mudou alguma propriedade de estado)
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

            if (blockEntity instanceof ManaAltarBlockEntity altar) {
                // Copia os itens da máquina para um "Contêiner Simples" que o Minecraft vanilla entende
                SimpleContainer inventory = new SimpleContainer(altar.itemHandler.getSlots());
                for (int i = 0; i < altar.itemHandler.getSlots(); i++) {
                    inventory.setItem(i, altar.itemHandler.getStackInSlot(i));
                }

                // O método mágico nativo que espalha os itens bonitinho no chão!
                Containers.dropContents(pLevel, pPos, inventory);

                // Atualiza a matemática do jogo ao redor
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            // Só agora deixa o bloco de fato sumir do mundo
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    // O método animateTick roda apenas na tela do jogador (Client) para gerar efeitos visuais
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(3) == 0) {
            double x = pPos.getX() + 0.5D + (pRandom.nextDouble() - 0.5D) * 0.5D;
            double y = pPos.getY() + 1.0D + (pRandom.nextDouble() * 0.5D);
            double z = pPos.getZ() + 0.5D + (pRandom.nextDouble() - 0.5D) * 0.5D;

            pLevel.addParticle(ParticleTypes.ENCHANT, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}