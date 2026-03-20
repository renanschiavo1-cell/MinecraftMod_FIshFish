package net.texugrosso.fishfish.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ManaPickaxeItem extends PickaxeItem {

    public ManaPickaxeItem(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    // Esse método roda TODA VEZ que a picareta quebra um bloco
    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        // Só queremos dropar itens extras no Servidor (para não bugar a tela duplicando itens falsos)
        if (!pLevel.isClientSide()) {

            // REGRA 1: É um minério de ferro? (Normal ou da Deepslate)
            if (pState.is(Blocks.IRON_ORE) || pState.is(Blocks.DEEPSLATE_IRON_ORE)) {
                // Cria a nossa "entidade" de item (o item jogado no chão) e joga no mundo!
                ItemEntity drop = new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(Moditems.CRYSTALLIZED_IRON.get(), 1));
                pLevel.addFreshEntity(drop);
            }

            // REGRA 2: É um minério de ouro?
            else if (pState.is(Blocks.GOLD_ORE) || pState.is(Blocks.DEEPSLATE_GOLD_ORE)) {
                ItemEntity drop = new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(Moditems.CRYSTALLIZED_GOLD.get(), 1));
                pLevel.addFreshEntity(drop);
            }
        }

        // Continua com o comportamento normal da picareta (dar dano de durabilidade, dropar o bloco original, etc)
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }
}