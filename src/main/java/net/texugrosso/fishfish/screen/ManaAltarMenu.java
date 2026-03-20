package net.texugrosso.fishfish.screen;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.texugrosso.fishfish.block.ModBlocks;
import net.texugrosso.fishfish.block.entity.ManaAltarBlockEntity;

public class ManaAltarMenu extends AbstractContainerMenu {
    public final ManaAltarBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data; // NOVO: Guarda os dados do rádio

    // Construtor do Cliente (Cria um rádio vazio para receber o sinal)
    public ManaAltarMenu(int pContainerId, Inventory inv, RegistryFriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    // Construtor do Servidor (Pega o rádio direto do Cérebro)
    public ManaAltarMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        this(pContainerId, inv, entity, ((ManaAltarBlockEntity) entity).data);
    }

    // O Construtor Mestre que junta tudo
    private ManaAltarMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MANA_ALTAR_MENU.get(), pContainerId);
        blockEntity = (ManaAltarBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        // Voltando para as SUAS coordenadas perfeitas!
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 48, 26));
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 109, 27));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data); // O SEGREDO: Sincroniza o servidor com a tela do jogador!
    }

    // --- MATEMÁTICA DA BARRA GRÁFICA ---
    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);

        // Aumentei o tamanho do laser para 40 pixels, para ele ir exatamente
        // do final da primeira caixa (X=66) até o começo da segunda (X=109)
        int progressBarSize = 40;

        return maxProgress != 0 && progress != 0 ? progress * progressBarSize / maxProgress : 0;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(net.minecraft.world.inventory.ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.MANA_ALTAR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    // Lógica do Shift-Click
    private static final int TEASER_SLOT_COUNT = 2;
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < TEASER_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TEASER_SLOT_COUNT, 36 + TEASER_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < 36 + TEASER_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, 0, TEASER_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
}