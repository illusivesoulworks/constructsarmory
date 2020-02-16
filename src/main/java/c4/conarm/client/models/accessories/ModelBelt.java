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

import javax.annotation.Nonnull;

public class ModelBelt extends ModelBase {

    public ModelRenderer anchor;
    public ModelRenderer belt;
    public ModelRenderer buckle;

    public ModelBelt() {
        this.textureHeight=32;
        this.textureWidth=32;

        this.anchor = new ModelRenderer(this, 0, 0);

        this.belt = new ModelRenderer(this, 0, 0);
        this.belt.setRotationPoint(0.0F, 9.5F, 0.0F);
        this.belt.addBox(-4.5F, 0.0F, -2.5F, 9, 2, 5, 0.7F);
        this.buckle = new ModelRenderer(this, 0, 7);
        this.buckle.setRotationPoint(0.0F, 9.5F, 0.0F);
        this.buckle.addBox(-2F, -1F, -3.3F, 4, 4, 1, 0.4F);

        this.anchor.addChild(belt);
        this.anchor.addChild(buckle);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

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
