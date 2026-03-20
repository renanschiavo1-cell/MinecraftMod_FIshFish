package net.texugrosso.fishfish.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

// Nossa espada herda (extends) as regras de uma SwordItem padrão
public class ManaSwordItem extends SwordItem {

    public ManaSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    // Sobrescrevemos (Override) o método que é chamado quando acertamos alguém
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        // 1. Verifica se o alvo já tem o efeito de Lentidão
        MobEffectInstance currentEffect = target.getEffect(MobEffects.MOVEMENT_SLOWDOWN);

        // Se tiver, pega o nível atual. Se não tiver, começa no -1.
        int currentLevel = currentEffect != null ? currentEffect.getAmplifier() : -1;

        // O próximo nível será o atual + 1
        int nextLevel = currentLevel + 1;

        // 2. A Lógica da Cristalização
        if (nextLevel >= 4) {
            // Se chegou no nível 4 (5 golpes), a cristalização "estoura"!

            // Dropa os itens no mundo (na posição do alvo)
            target.spawnAtLocation(Items.PRISMARINE_SHARD, 2); // Dropa 2 Shards
            target.spawnAtLocation(Items.PRISMARINE_CRYSTALS, 1); // Dropa 1 Crystal

            // Remove o efeito para resetar a contagem
            target.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);

            // Opcional: Você pode até aplicar um dano extra aqui se quiser!

        } else {
            // Se ainda não chegou no máximo, aplica a Lentidão mais forte
            // 100 = Duração em "ticks" (100 ticks = 5 segundos)
            // nextLevel = A força da lentidão
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, nextLevel));
        }

        // Continua com o dano normal da espada
        return super.hurtEnemy(stack, target, attacker);
    }
}