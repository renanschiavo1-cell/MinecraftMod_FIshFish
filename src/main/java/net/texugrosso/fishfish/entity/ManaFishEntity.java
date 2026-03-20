package net.texugrosso.fishfish.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ManaFishEntity extends AbstractFish {

    public ManaFishEntity(EntityType<? extends AbstractFish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // --- INTELIGÊNCIA ARTIFICIAL (O que ele faz) ---
    @Override
    protected void registerGoals() {
        super.registerGoals();
        // 0 é a prioridade máxima. Se ele apanhar, ele foge nadando rápido!
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        // 1 é a prioridade normal. Ele fica nadando aleatoriamente pela água.
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 40));
    }

    // --- ATRIBUTOS (Vida, Velocidade, etc) ---
    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D) // 6.0D = 3 Corações
                .add(Attributes.MOVEMENT_SPEED, 0.5D); // Velocidade de nado
    }

    // --- ITEM DO BALDE ---
    // Todo peixe no Minecraft pode ser pego num balde.
    // Por enquanto, ele vai devolver um balde de água normal se tentarem pegar ele.
    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(Items.WATER_BUCKET);
    }

    // --- SONS DO PEIXE ---
    // Usamos os sons do Bacalhau (Cod) como base para não ficar mudo.
    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP; // Som dele batendo no chão seco
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COD_HURT;
    }
}