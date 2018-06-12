/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.client.models.accessories;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelKnapsack extends ModelBase {

    public ModelRenderer knapsack;

    public ModelKnapsack() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.knapsack = new ModelRenderer(this, 0, 0);
        this.knapsack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.knapsack.addBox(-3.5F, 0.0F, 2.5F, 7, 9, 5, 0.5F);
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        if (entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
            setRotateAngle(knapsack, 0.5F, 0.0F, 0.0F);
        } else {
            setRotateAngle(knapsack, 0.0F, 0.0F, 0.0F);
        }
        this.knapsack.render(f5);
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
