package c4.conarm.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;
import javax.jws.WebParam;

public class ModelCoreArmor extends ModelArmorBase {

    public String part;

    public ModelRenderer legLeftAnchor;
    public ModelRenderer legRightAnchor;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer bootLeftAnchor;
    public ModelRenderer bootRightAnchor;
    public ModelRenderer bootLeft;
    public ModelRenderer bootRight;
    public ModelRenderer chestAnchor;
    public ModelRenderer chest;
    public ModelRenderer helmet;

    public ModelCoreArmor(EntityEquipmentSlot slot, String partIn, int textureWidth, int textureHeight, int offsetX, int offsetY) {

        super(slot);

        this.part = partIn;

        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        //Helmet
        this.helmet = new ModelRenderer(this, offsetX + 32, offsetY + 48);
        this.helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.1F);

        //Chestplate
        this.chestAnchor = new ModelRenderer(this, offsetX, offsetY);
        this.chest = new ModelRenderer(this, offsetX, offsetY + 55);
        this.chest.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 5, 4, 0.1F);

        //Leggings
        this.legLeftAnchor = new ModelRenderer(this, offsetX, offsetY);
        this.legRightAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.legRight = new ModelRenderer(this, offsetX, offsetY + 40);
        this.legRight.mirror = true;
        this.legRight.setRotationPoint(-1.9F, 0.0F, 0.0F);
        this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.1F);

        this.legLeft = new ModelRenderer(this, offsetX, offsetY + 40);
        this.legLeft.setRotationPoint(1.9F, 0.0F, 0.0F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.1F);

        //Boots
        this.bootLeftAnchor = new ModelRenderer(this, offsetX, offsetY);
        this.bootRightAnchor = new ModelRenderer(this, offsetX, offsetY);

        this.bootLeft = new ModelRenderer(this, offsetX + 16, offsetY + 43);
        this.bootLeft.setRotationPoint(1.9F, 6.0F, 0.0F);
        this.bootLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.4F);

        this.bootRight = new ModelRenderer(this, offsetX + 16, offsetY + 43);
        this.bootRight.mirror = true;
        this.bootRight.setRotationPoint(-1.9F, 6.0F, 0.0F);
        this.bootRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.4F);

        //Hierarchy
        this.chestAnchor.addChild(this.chest);
        this.legLeftAnchor.addChild(this.legLeft);
        this.legRightAnchor.addChild(this.legRight);
        this.bootLeftAnchor.addChild(this.bootLeft);
        this.bootRightAnchor.addChild(this.bootRight);
    }

    @Override
    public void render(@Nullable Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        helmet.showModel = slot == EntityEquipmentSlot.HEAD;
        chestAnchor.showModel = slot == EntityEquipmentSlot.CHEST;
        legLeftAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        legRightAnchor.showModel = slot == EntityEquipmentSlot.LEGS;
        bootLeftAnchor.showModel = slot == EntityEquipmentSlot.FEET;
        bootRightAnchor.showModel = slot == EntityEquipmentSlot.FEET;
        bipedHeadwear.showModel = false;
        bipedLeftArm.showModel = false;
        bipedRightArm.showModel = false;

        bipedHead = helmet;
        bipedBody = chestAnchor;

        if (slot == EntityEquipmentSlot.LEGS) {
            bipedLeftLeg = legLeftAnchor;
            bipedRightLeg = legRightAnchor;
        } else {
            bipedLeftLeg = bootLeftAnchor;
            bipedRightLeg = bootRightAnchor;
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
