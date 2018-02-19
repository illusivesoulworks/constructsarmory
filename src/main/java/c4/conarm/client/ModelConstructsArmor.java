package c4.conarm.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;

/**
 * Created using Tabula 7.0.0
 */
public class ModelConstructsArmor extends ModelBiped {

    public EntityEquipmentSlot slot;
    public String part;

    public ModelRenderer pantsAnchor;
    public ModelRenderer Belt;
    public ModelRenderer LeftSkirt;
    public ModelRenderer RightSkirt;
    public ModelRenderer FrontSkirt;
    public ModelRenderer BackSkirt;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightLeg;

    public ModelRenderer LeftBoot;
    public ModelRenderer RightBoot;
    public ModelRenderer LeftBootPlate;
    public ModelRenderer RightBootPlate;

    public ModelRenderer chestAnchor;
    public ModelRenderer ChestBottom;
    public ModelRenderer ChestTop;
    public ModelRenderer ChestFront;
    public ModelRenderer ChestBack;

    public ModelRenderer leftArmAnchor;
    public ModelRenderer rightArmAnchor;
    public ModelRenderer LeftShoulder;
    public ModelRenderer LeftShoulderExtra;
    public ModelRenderer LeftGauntlet;
    public ModelRenderer RightShoulder;
    public ModelRenderer RightShoulderExtra;
    public ModelRenderer RightGauntlet;

    public ModelRenderer headAnchor;
    public ModelRenderer Helmet;
    public ModelRenderer FacePlate;

    public ModelConstructsArmor(EntityEquipmentSlot slot, String partIn, int textureWidth, int textureHeight, int offsetX, int offsetY) {

        this.slot = slot;
        this.part = partIn;

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        //Helmet
        this.headAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.FacePlate = new ModelRenderer(this, offsetX + 3, offsetY + 10);
        this.FacePlate.setRotationPoint(0.0F, 0.0F, -1.5F);
        this.FacePlate.addBox(-4.0F, -8.0F, -2.5F, 8, 8, 5, 0.4F);

        this.Helmet = new ModelRenderer(this, offsetX + 32, offsetY + 48);
        this.Helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.1F);

        //Chestplate
        this.chestAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.ChestBack = new ModelRenderer(this, offsetX + 50, offsetY + 13);
        this.ChestBack.setRotationPoint(0.0F, 6.1F, 2.3F);
        this.ChestBack.addBox(-3.0F, 0.0F, -0.5F, 6, 3, 1, 0.1F);

        this.ChestTop = new ModelRenderer(this, offsetX, offsetY + 23);
        this.ChestTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ChestTop.addBox(-5.0F, 0.0F, -3.0F, 10, 6, 6, 0.1F);

        this.ChestFront = new ModelRenderer(this, offsetX + 50, offsetY + 13);
        this.ChestFront.setRotationPoint(0.0F, 6.1F, -2.3F);
        this.ChestFront.addBox(-3.0F, 0.0F, -0.5F, 6, 3, 1, 0.1F);

        this.ChestBottom = new ModelRenderer(this, offsetX, offsetY + 55);
        this.ChestBottom.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.ChestBottom.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.1F);

        this.leftArmAnchor = new ModelRenderer(this, offsetX, offsetY);
        this.leftArmAnchor.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.rightArmAnchor = new ModelRenderer(this, offsetX, offsetY);
        this.rightArmAnchor.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.LeftGauntlet = new ModelRenderer(this, offsetX + 36, offsetY);
        this.LeftGauntlet.setRotationPoint(0.0F, 7.50F, 0.0F);
        this.LeftGauntlet.addBox(-1.0F, -2.0F, -2.5F, 4, 5, 5, 0.2F);

        this.RightGauntlet = new ModelRenderer(this, offsetX + 36, offsetY);
        this.RightGauntlet.mirror = true;
        this.RightGauntlet.setRotationPoint(0.0F, 7.50F, 0.0F);
        this.RightGauntlet.addBox(-3.0F, -2.0F, -2.5F, 4, 5, 5, 0.2F);

        this.LeftShoulder = new ModelRenderer(this, offsetX + 44, offsetY + 28);
        this.LeftShoulder.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.LeftShoulder.addBox(-1.0F, -2.0F, -2.5F, 5, 4, 5, 0.2F);
        this.setRotateAngle(LeftShoulder, 0.0F, 0.0F, -0.17453292519943295F);

        this.RightShoulder = new ModelRenderer(this, offsetX + 44, offsetY + 28);
        this.RightShoulder.mirror = true;
        this.RightShoulder.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.RightShoulder.addBox(-4.0F, -2.0F, -2.5F, 5, 4, 5, 0.2F);
        this.setRotateAngle(RightShoulder, 0.0F, 0.0F, 0.17453292519943295F);

        this.RightShoulderExtra = new ModelRenderer(this, offsetX + 52, offsetY + 20);
        this.RightShoulderExtra.mirror = true;
        this.RightShoulderExtra.setRotationPoint(-2.6F, -1.5F, 0.0F);
        this.RightShoulderExtra.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 4, 0.2F);
        this.setRotateAngle(RightShoulderExtra, 0.0F, 0.0F, 0.08726646259971647F);

        this.LeftShoulderExtra = new ModelRenderer(this, offsetX + 52, offsetY + 20);
        this.LeftShoulderExtra.setRotationPoint(2.6F, -1.5F, 0.0F);
        this.LeftShoulderExtra.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 4, 0.2F);
        this.setRotateAngle(LeftShoulderExtra, 0.0F, 0.0F, -0.08726646259971647F);

        //Leggings
        this.pantsAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.Belt = new ModelRenderer(this, offsetX, offsetY);
        this.Belt.setRotationPoint(0.0F, 9.5F, 0.0F);
        this.Belt.addBox(-4.5F, 0.0F, -2.5F, 9, 2, 5, 0.0F);

        this.FrontSkirt = new ModelRenderer(this, offsetX + 54, offsetY + 7);
        this.FrontSkirt.setRotationPoint(0.0F, 11.5F, -2.5F);
        this.FrontSkirt.addBox(-2.0F, 0.0F, -0.5F, 4, 5, 1, 0.0F);
        this.setRotateAngle(FrontSkirt, -0.08726646259971647F, 0.0F, 0.0F);

        this.RightSkirt = new ModelRenderer(this, offsetX + 36, offsetY + 10);
        this.RightSkirt.mirror = true;
        this.RightSkirt.setRotationPoint(-4.5F, 11.5F, 0.0F);
        this.RightSkirt.addBox(-0.5F, 0.0F, -2.5F, 2, 6, 5, 0.0F);
        this.setRotateAngle(RightSkirt, 0.0F, 0.0F, 0.20943951023931953F);

        this.LeftSkirt = new ModelRenderer(this, offsetX + 36, offsetY + 10);
        this.LeftSkirt.setRotationPoint(4.5F, 11.5F, 0.0F);
        this.LeftSkirt.addBox(-1.5F, 0.0F, -2.5F, 2, 6, 5, 0.0F);
        this.setRotateAngle(LeftSkirt, 0.0F, 0.0F, -0.20943951023931953F);

        this.BackSkirt = new ModelRenderer(this, offsetX + 54, offsetY);
        this.BackSkirt.setRotationPoint(0.0F, 11.5F, 2.5F);
        this.BackSkirt.addBox(-2.0F, 0.0F, -0.5F, 4, 6, 1, 0.0F);
        this.setRotateAngle(BackSkirt, 0.08726646259971647F, 0.0F, 0.0F);

        this.RightLeg = new ModelRenderer(this, offsetX, offsetY + 40);
        this.RightLeg.mirror = true;
        this.RightLeg.setRotationPoint(-1.9F, 10.8F, 0.0F);
        this.RightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.1F);

        this.LeftLeg = new ModelRenderer(this, offsetX, offsetY + 40);
        this.LeftLeg.setRotationPoint(1.9F, 10.8F, 0.0F);
        this.LeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.1F);

        //Boots
        this.RightBootPlate = new ModelRenderer(this, offsetX + 38, offsetY + 21);
        this.RightBootPlate.mirror = true;
        this.RightBootPlate.setRotationPoint(-0.10F, 5.1F, -2.0F);
        this.RightBootPlate.addBox(-2.5F, 0.0F, -1.0F, 5, 5, 2, 0.0F);

        this.LeftBootPlate = new ModelRenderer(this, offsetX + 38, offsetY + 21);
        this.LeftBootPlate.setRotationPoint(0.10F, 5.1F, -2.0F);
        this.LeftBootPlate.addBox(-2.5F, 0.0F, -1.0F, 5, 5, 2, 0.0F);

        this.LeftBoot = new ModelRenderer(this, offsetX + 16, offsetY + 37);
        this.LeftBoot.setRotationPoint(1.9F, 0.0F, 0.0F);
        this.LeftBoot.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.4F);

        this.RightBoot = new ModelRenderer(this, offsetX + 16, offsetY + 37);
        this.RightBoot.mirror = true;
        this.RightBoot.setRotationPoint(-1.9F, 0.0F, 0.0F);
        this.RightBoot.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.4F);

        //Hierarchy
        this.headAnchor.addChild(this.Helmet);
        this.headAnchor.addChild(this.FacePlate);

        this.chestAnchor.addChild(this.ChestTop);
        this.ChestTop.addChild(this.ChestBack);
        this.ChestTop.addChild(this.ChestBottom);
        this.ChestTop.addChild(this.ChestFront);
        this.leftArmAnchor.addChild(this.LeftShoulder);
        this.leftArmAnchor.addChild(this.LeftShoulderExtra);
        this.leftArmAnchor.addChild(this.LeftGauntlet);
        this.rightArmAnchor.addChild(this.RightShoulder);
        this.rightArmAnchor.addChild(this.RightShoulderExtra);
        this.rightArmAnchor.addChild(this.RightGauntlet);

        this.pantsAnchor.addChild(this.Belt);
        this.pantsAnchor.addChild(this.BackSkirt);
        this.pantsAnchor.addChild(this.FrontSkirt);
        this.pantsAnchor.addChild(this.LeftSkirt);
        this.pantsAnchor.addChild(this.RightSkirt);

        this.LeftBoot.addChild(this.LeftBootPlate);
        this.RightBoot.addChild(this.RightBootPlate);
    }

    @Override
    public void render(@Nullable Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        headAnchor.showModel = slot == EntityEquipmentSlot.HEAD;
        Helmet.showModel = part.equals("core");
        chestAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        ChestBottom.showModel = chestAnchor.showModel && part.equals("core");
        rightArmAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        leftArmAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        pantsAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        LeftLeg.showModel = pantsAnchor.showModel && part.equals("core");
        RightLeg.showModel = pantsAnchor.showModel && part.equals("core");
        LeftBoot.showModel = slot == EntityEquipmentSlot.FEET;
        RightBoot.showModel = slot == EntityEquipmentSlot.FEET;
        bipedHeadwear.showModel = false;

        bipedHead = headAnchor;
        bipedBody = chestAnchor;
        bipedRightArm = rightArmAnchor;
        bipedLeftArm = leftArmAnchor;

        if (slot == EntityEquipmentSlot.LEGS) {
            bipedBody = pantsAnchor;
            bipedLeftLeg = LeftLeg;
            bipedRightLeg = RightLeg;
        } else {
            bipedLeftLeg = LeftBoot;
            bipedRightLeg = RightBoot;
        }

        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
