package c4.conarm.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public class ModelDummy extends ModelBiped {

    public ModelDummy() {}

    @Override
    public void render(@Nullable Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        //NO-OP
    }
}
