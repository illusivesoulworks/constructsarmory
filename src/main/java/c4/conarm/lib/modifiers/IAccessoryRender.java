package c4.conarm.lib.modifiers;

import net.minecraft.entity.EntityLivingBase;

public interface IAccessoryRender {

    void onAccessoryRender(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);

}
