/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.client.models.accessories;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * Cloak - Undefined
 * Created using Tabula 7.0.0
 */
public class ModelCloak extends ModelBase {

    public ModelRenderer right;
    public ModelRenderer left;
    public ModelRenderer hood;
    public ModelRenderer anchor;

    public ModelCloak() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.anchor = new ModelRenderer(this, 0, 0);

        this.hood = new ModelRenderer(this, 0, 22);
        this.hood.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hood.addBox(-5.0F, -3.5F, -3.0F, 10, 5, 13, 0.45F);
        this.setRotateAngle(hood, 0.13962634015954636F, 0.0F, 0.0F);

        this.right = new ModelRenderer(this, 0, 0);
        this.right.mirror = true;
        this.right.setRotationPoint(5.0F, 0.0F, 0.0F);
        this.right.addBox(-5.0F, -3.5F, -4.0F, 10, 12, 10, 0.45F);
        this.setRotateAngle(right, 0.0F, -0.13962634015954636F, -0.06981317007977318F);

        this.left = new ModelRenderer(this, 0, 40);
        this.left.setRotationPoint(-5.0F, 0.0F, 0.0F);
        this.left.addBox(-5.0F, -3.5F, -4.0F, 10, 7, 10, 0.45F);
        this.setRotateAngle(left, 0.0F, 0.13962634015954636F, 0.06981317007977318F);

        this.anchor.addChild(hood);
        this.anchor.addChild(right);
        this.anchor.addChild(left);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
            setRotateAngle(anchor, 0.5F, 0.0F, 0.0F);
        } else {
            setRotateAngle(anchor, 0.0F, 0.0F, 0.0F);
        }
        this.anchor.render(f5);
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
