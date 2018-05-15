/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.client.models.accessories;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelGoggles extends ModelBase {

    public ModelRenderer goggles;

    public ModelGoggles() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.goggles = new ModelRenderer(this, 0, 0);
        this.goggles.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.goggles.addBox(-5F, -5.5F, -5.7F, 10, 4, 10, 0.4F);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        if (entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
            setRotateAngle(goggles, 0.5F, 0.0F, 0.0F);
        } else {
            setRotateAngle(goggles, 0.0F, 0.0F, 0.0F);
        }
        this.goggles.render(f5);
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
