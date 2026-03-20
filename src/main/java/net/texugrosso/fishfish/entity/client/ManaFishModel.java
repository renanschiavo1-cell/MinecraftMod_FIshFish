package net.texugrosso.fishfish.entity.client;

// Estes imports resolvem os erros de "Cannot resolve symbol"
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ManaFishModel<T extends Entity> extends EntityModel<T> {

    // Registra onde o modelo fica salvo na memória do jogo
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("fishfish", "mana_fish"), "main");

    private final ModelPart body;
    private final ModelPart tail;

    public ManaFishModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }

    // A geometria que você criou no Blockbench!
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.5F, -1.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-2.0F, 0.0F, -1.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 20.5F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-7.0F, 19.775F, 1.0F));

        PartDefinition nadadeira_r1 = tail.addOrReplaceChild("nadadeira_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-5.0F, 0.5F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition fim_calda_r1 = tail.addOrReplaceChild("fim_calda_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition base_calda_r1 = tail.addOrReplaceChild("base_calda_r1", CubeListBuilder.create().texOffs(12, 10).addBox(-1.0F, -1.5F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.475F, -1.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Faz o corpo olhar para onde ele está nadando (Cima/Baixo/Lados)
        this.body.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.body.xRot = headPitch * ((float)Math.PI / 180F);

        // A Mágica do Rabo: Cosseno cria o balanço de um lado pro outro baseado na velocidade (limbSwing)
        this.tail.yRot = net.minecraft.util.Mth.cos(limbSwing * 0.6F) * 0.8F * limbSwingAmount;
    }

    // O método de renderização atualizado para o Minecraft 1.21 (usando int color)
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}