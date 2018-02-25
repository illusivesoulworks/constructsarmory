package c4.conarm.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;

public class ModelPlateArmor extends ModelArmorBase {

    public String part;

    public ModelRenderer pantsAnchor;
    public ModelRenderer belt;
    public ModelRenderer skirtLeft;
    public ModelRenderer skirtRight;
    public ModelRenderer skirtFront;
    public ModelRenderer skirtBack;

    public ModelRenderer bootsAnchorLeft;
    public ModelRenderer bootsAnchorRight;
    public ModelRenderer bootPlateLeft;
    public ModelRenderer bootPlateRight;

    public ModelRenderer chestAnchor;
    public ModelRenderer chestTop;
    public ModelRenderer chestFront;
    public ModelRenderer chestBack;

    public ModelRenderer armAnchorLeft;
    public ModelRenderer armAnchorRight;
    public ModelRenderer shoulderLeft;
    public ModelRenderer shoulderLeftEx;
    public ModelRenderer gauntletLeft;
    public ModelRenderer shoulderRight;
    public ModelRenderer shoulderRightEx;
    public ModelRenderer gauntletRight;

    public ModelRenderer headAnchor;
    public ModelRenderer facePlate;

    public ModelPlateArmor(EntityEquipmentSlot slot, String partIn, int textureWidth, int textureHeight, int offsetX, int offsetY) {

        super(slot);

        this.part = partIn;

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        //Helmet
        this.headAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.facePlate = new ModelRenderer(this, offsetX + 3, offsetY + 10);
        this.facePlate.setRotationPoint(0.0F, 0.0F, -1.5F);
        this.facePlate.addBox(-4.0F, -8.0F, -2.5F, 8, 8, 5, 0.4F);

        //Chestplate
        this.chestAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.chestBack = new ModelRenderer(this, offsetX + 50, offsetY + 13);
        this.chestBack.setRotationPoint(0.0F, 6.1F, 2.3F);
        this.chestBack.addBox(-3.0F, 0.0F, -0.5F, 6, 3, 1, 0.1F);

        this.chestTop = new ModelRenderer(this, offsetX, offsetY + 23);
        this.chestTop.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chestTop.addBox(-5.0F, 0.0F, -3.0F, 10, 6, 6, 0.1F);

        this.chestFront = new ModelRenderer(this, offsetX + 50, offsetY + 13);
        this.chestFront.setRotationPoint(0.0F, 6.1F, -2.3F);
        this.chestFront.addBox(-3.0F, 0.0F, -0.5F, 6, 3, 1, 0.1F);

        this.armAnchorLeft = new ModelRenderer(this, offsetX, offsetY);
        this.armAnchorLeft.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.armAnchorRight = new ModelRenderer(this, offsetX, offsetY);
        this.armAnchorRight.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.gauntletLeft = new ModelRenderer(this, offsetX + 36, offsetY);
        this.gauntletLeft.setRotationPoint(0.0F, 7.50F, 0.0F);
        this.gauntletLeft.addBox(-1.0F, -2.0F, -2.5F, 4, 5, 5, 0.2F);

        this.gauntletRight = new ModelRenderer(this, offsetX + 36, offsetY);
        this.gauntletRight.mirror = true;
        this.gauntletRight.setRotationPoint(0.0F, 7.50F, 0.0F);
        this.gauntletRight.addBox(-3.0F, -2.0F, -2.5F, 4, 5, 5, 0.2F);

        this.shoulderLeft = new ModelRenderer(this, offsetX + 44, offsetY + 28);
        this.shoulderLeft.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shoulderLeft.addBox(-1.0F, -2.0F, -2.5F, 5, 4, 5, 0.2F);
        this.setRotateAngle(shoulderLeft, 0.0F, 0.0F, -0.17453292519943295F);

        this.shoulderRight = new ModelRenderer(this, offsetX + 44, offsetY + 28);
        this.shoulderRight.mirror = true;
        this.shoulderRight.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.shoulderRight.addBox(-4.0F, -2.0F, -2.5F, 5, 4, 5, 0.2F);
        this.setRotateAngle(shoulderRight, 0.0F, 0.0F, 0.17453292519943295F);

        this.shoulderRightEx = new ModelRenderer(this, offsetX + 52, offsetY + 20);
        this.shoulderRightEx.mirror = true;
        this.shoulderRightEx.setRotationPoint(-2.6F, -1.5F, 0.0F);
        this.shoulderRightEx.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 4, 0.2F);
        this.setRotateAngle(shoulderRightEx, 0.0F, 0.0F, 0.08726646259971647F);

        this.shoulderLeftEx = new ModelRenderer(this, offsetX + 52, offsetY + 20);
        this.shoulderLeftEx.setRotationPoint(2.6F, -1.5F, 0.0F);
        this.shoulderLeftEx.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 4, 0.2F);
        this.setRotateAngle(shoulderLeftEx, 0.0F, 0.0F, -0.08726646259971647F);

        //Leggings
        this.pantsAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.belt = new ModelRenderer(this, offsetX, offsetY);
        this.belt.setRotationPoint(0.0F, 9.5F, 0.0F);
        this.belt.addBox(-4.5F, 0.0F, -2.5F, 9, 2, 5, 0.0F);

        this.skirtFront = new ModelRenderer(this, offsetX + 54, offsetY + 7);
        this.skirtFront.setRotationPoint(0.0F, 11.5F, -2.5F);
        this.skirtFront.addBox(-2.0F, 0.0F, -0.5F, 4, 5, 1, 0.0F);
        this.setRotateAngle(skirtFront, -0.08726646259971647F, 0.0F, 0.0F);

        this.skirtRight = new ModelRenderer(this, offsetX + 36, offsetY + 10);
        this.skirtRight.mirror = true;
        this.skirtRight.setRotationPoint(-4.5F, 11.5F, 0.0F);
        this.skirtRight.addBox(-0.5F, 0.0F, -2.5F, 2, 6, 5, 0.0F);
        this.setRotateAngle(skirtRight, 0.0F, 0.0F, 0.20943951023931953F);

        this.skirtLeft = new ModelRenderer(this, offsetX + 36, offsetY + 10);
        this.skirtLeft.setRotationPoint(4.5F, 11.5F, 0.0F);
        this.skirtLeft.addBox(-1.5F, 0.0F, -2.5F, 2, 6, 5, 0.0F);
        this.setRotateAngle(skirtLeft, 0.0F, 0.0F, -0.20943951023931953F);

        this.skirtBack = new ModelRenderer(this, offsetX + 54, offsetY);
        this.skirtBack.setRotationPoint(0.0F, 11.5F, 2.5F);
        this.skirtBack.addBox(-2.0F, 0.0F, -0.5F, 4, 6, 1, 0.0F);
        this.setRotateAngle(skirtBack, 0.08726646259971647F, 0.0F, 0.0F);

        //Boots
        this.bootsAnchorLeft = new ModelRenderer(this, offsetX, offsetY);
        this.bootsAnchorRight = new ModelRenderer(this, offsetX, offsetY);

        this.bootPlateRight = new ModelRenderer(this, offsetX + 38, offsetY + 21);
        this.bootPlateRight.mirror = true;
        this.bootPlateRight.setRotationPoint(-2.0F, 5.1F, -2.0F);
        this.bootPlateRight.addBox(-2.5F, 0.0F, -1.0F, 5, 5, 2, 0.0F);

        this.bootPlateLeft = new ModelRenderer(this, offsetX + 38, offsetY + 21);
        this.bootPlateLeft.setRotationPoint(2.0F, 5.1F, -2.0F);
        this.bootPlateLeft.addBox(-2.5F, 0.0F, -1.0F, 5, 5, 2, 0.0F);

        //Hierarchy
        this.chestAnchor.addChild(this.chestTop);
        this.chestTop.addChild(this.chestBack);
        this.chestTop.addChild(this.chestFront);
        this.armAnchorLeft.addChild(this.shoulderLeft);
        this.armAnchorLeft.addChild(this.shoulderLeftEx);
        this.armAnchorLeft.addChild(this.gauntletLeft);
        this.armAnchorRight.addChild(this.shoulderRight);
        this.armAnchorRight.addChild(this.shoulderRightEx);
        this.armAnchorRight.addChild(this.gauntletRight);

        this.pantsAnchor.addChild(this.belt);
        this.pantsAnchor.addChild(this.skirtBack);
        this.pantsAnchor.addChild(this.skirtFront);
        this.pantsAnchor.addChild(this.skirtLeft);
        this.pantsAnchor.addChild(this.skirtRight);

        this.headAnchor.addChild(this.facePlate);

        this.bootsAnchorLeft.addChild(this.bootPlateLeft);
        this.bootsAnchorRight.addChild(this.bootPlateRight);
    }

    @Override
    public void render(@Nullable Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        headAnchor.showModel = slot == EntityEquipmentSlot.HEAD;
        chestAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        armAnchorRight.showModel = slot == EntityEquipmentSlot.CHEST;
        armAnchorLeft.showModel = slot == EntityEquipmentSlot.CHEST;
        pantsAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        bootsAnchorLeft.showModel = slot == EntityEquipmentSlot.FEET;
        bootsAnchorRight.showModel = slot == EntityEquipmentSlot.FEET;
        bipedHeadwear.showModel = false;

        bipedHead = headAnchor;
        bipedBody = chestAnchor;
        bipedRightArm = armAnchorRight;
        bipedLeftArm = armAnchorLeft;

        if (slot == EntityEquipmentSlot.LEGS) {
            bipedBody = pantsAnchor;
            bipedLeftLeg.showModel = false;
            bipedRightLeg.showModel = false;
        } else {
            bipedLeftLeg = bootsAnchorLeft;
            bipedRightLeg = bootsAnchorRight;
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
