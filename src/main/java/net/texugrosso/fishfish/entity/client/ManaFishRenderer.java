package net.texugrosso.fishfish.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.texugrosso.fishfish.entity.ManaFishEntity;

public class ManaFishRenderer extends MobRenderer<ManaFishEntity, ManaFishModel<ManaFishEntity>> {

    // O endereço exato de onde a imagem da "Pele" do peixe foi salva!
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("fishfish", "textures/entity/mana_fish.png");

    public ManaFishRenderer(EntityRendererProvider.Context pContext) {
        // Aqui conectamos o Renderizador com o "Esqueleto" (Model)
        // O 0.3f no final é o tamanho da sombra redonda dele no chão!
        super(pContext, new ManaFishModel<>(pContext.bakeLayer(ManaFishModel.LAYER_LOCATION)), 0.3f);
    }

    // Método obrigatório que diz qual imagem (Textura PNG) deve cobrir o modelo
    @Override
    public ResourceLocation getTextureLocation(ManaFishEntity pEntity) {
        return TEXTURE;
    }

    // (Opcional) Esse método roda todo frame. Se o peixe ficar gigante ou minúsculo no jogo,
    // nós podemos usar o pPoseStack.scale() aqui dentro para ajustar o tamanho dele!
    @Override
    public void render(ManaFishEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}