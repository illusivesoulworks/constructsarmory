package c4.conarm.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelConstructArmor extends ModelBiped {

    public ModelRenderer helmet;

    public ModelRenderer chestAnchor;
    public ModelRenderer chestTop;
    public ModelRenderer chestBottom;

    public ModelConstructArmor() {
        this(0.0F);
    }

    public ModelConstructArmor(float modelSize) {
        this(modelSize, 0.0F, 64, 64, 0, 0);
    }

    public ModelConstructArmor(float modelSize, int textureOffsetX, int textureOffsetY) {
        this(modelSize, 0.0F, 64, 64, textureOffsetX, textureOffsetY);
    }

    public ModelConstructArmor(float modelSize, int textureWidthIn, int textureHeightIn, int textureOffsetX, int textureOffsetY) {
        this(modelSize, 0.0F, textureWidthIn, textureHeightIn, textureOffsetX, textureOffsetY);
    }

    public ModelConstructArmor(float modelSize, float rotation, int textureWidthIn, int textureHeightIn, int textureOffsetX, int textureOffsetY) {
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        this.bipedHead = new ModelRenderer(this, textureOffsetX, textureOffsetY);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, textureOffsetX + 32, textureOffsetY);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);

        this.bipedBody = new ModelRenderer(this, textureOffsetX + 16, textureOffsetY + 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + rotation, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, textureOffsetX + 40, textureOffsetY + 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + rotation, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, textureOffsetX + 40, textureOffsetY + 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + rotation, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, textureOffsetX, textureOffsetY + 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + rotation, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, textureOffsetX, textureOffsetY + 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + rotation, 0.0F);
    }
}
