package c4.conarm.client;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;

@SideOnly(Side.CLIENT)
public class LayerConstructArmor implements LayerRenderer<EntityLivingBase> {

    protected ModelConstructArmor modelSmallArmor = new ModelConstructArmor(0.5F);
    protected ModelConstructArmor modelArmor = new ModelConstructArmor(1.0F);
    private final RenderLivingBase<?> renderer;
    public static int textureMapWidth;
    public static int textureMapHeight;

    public LayerConstructArmor(RenderLivingBase<?> rendererIn)
    {
        this.renderer = rendererIn;
    }

    public static void calculateTextureSize() {
        TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite sprite = map.getTextureExtry("conarm:models/armor/armor_main");
        if (sprite != null) {
            textureMapWidth = (int) (sprite.getIconWidth() / (sprite.getMaxU() - sprite.getMinU()));
            textureMapHeight = (int) (sprite.getIconHeight() / (sprite.getMaxV() - sprite.getMinV()));
        } else {
            textureMapWidth = 0;
            textureMapHeight = 0;
        }
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }

    /*Don't kill me, I'm working on this. I know this rendering code is complete garbage :P*/
    private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

        if (itemstack.getItem() instanceof TinkersArmor)
        {
            TinkersArmor tinkersArmor = (TinkersArmor) itemstack.getItem();

            if (tinkersArmor.getEquipmentSlot() == slotIn)
            {
                List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(itemstack));

                for (int i = 0; i < materials.size(); i++) {

                    Material material = materials.get(i);
                    String identifier = material.getIdentifier();
                    String partIn;

                    this.renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

                    switch (i) {
                        case 0: partIn = "core"; break;
                        case 1: partIn = "plate"; break;
                        case 2:
                            if (materials.size() > 3) {
                                partIn = "plate";
                            } else {
                                partIn = "trim";
                            }
                            break;
                        default: partIn = "trim"; break;
                    }

                    TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
                    TextureAtlasSprite sprite;
                    String loc = "conarm:models/armor/armor_main";
                    if (partIn.equals("trim")) {
                        loc = "conarm:models/armor/armor_trim";
                    }

                    sprite = map.getTextureExtry(String.format("%s_%s",loc,identifier));

                    if (sprite == null) {
                        sprite = map.getTextureExtry(loc);
                        if (sprite == null) {
                            return;
                        }
                    }

                    float R = 1.0F;
                    float G = 1.0F;
                    float B = 1.0F;
                    float A = 1.0F;

                    if (material.renderInfo.useVertexColoring() && !material.identifier.equals("cactus")) {
                        int color = material.renderInfo.getVertexColor();
                        int a = (color >> 24);
                        if(a == 0) {
                            a = 255;
                        }
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = (color) & 0xFF;
                        R = (float)r/255f;
                        G = (float)g/255f;
                        B = (float)b/255f;
                        A = (float)a/255f;
                    }

                    ModelBiped armor;
                    if (partIn.equals("core")) {
                        armor = new ModelCoreArmor(slotIn, partIn, textureMapWidth, textureMapHeight, sprite.getOriginX(), sprite.getOriginY());
                    }
                    else {
                        armor = new ModelPlateArmor(slotIn, partIn, textureMapWidth, textureMapHeight, sprite.getOriginX(), sprite.getOriginY());
                    }
                    armor.setModelAttributes(this.renderer.getMainModel());
                    armor.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                    GlStateManager.color(R, G, B, A);
                    armor.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                }
            }
        }
    }

    protected void setModelSlotVisible(ModelConstructsArmor modelArmor, EntityEquipmentSlot slotIn)
    {
        this.setModelVisible(modelArmor);

        switch (slotIn)
        {
            case HEAD:
                modelArmor.bipedHead.showModel = true;
                modelArmor.bipedHeadwear.showModel = true;
                break;
            case CHEST:
                modelArmor.bipedBody.showModel = true;
                modelArmor.bipedRightArm.showModel = true;
                modelArmor.bipedLeftArm.showModel = true;
                break;
            case LEGS:
                modelArmor.bipedBody.showModel = true;
                modelArmor.bipedRightLeg.showModel = true;
                modelArmor.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                modelArmor.bipedRightLeg.showModel = true;
                modelArmor.bipedLeftLeg.showModel = true;
        }
    }

    protected void setModelVisible(ModelConstructsArmor model)
    {
        model.setVisible(false);
    }

//    public ModelConstructArmor getModelFromSlot(EntityEquipmentSlot slotIn, int textureWidth, int textureHeight, int textureX, int textureY)
//    {
//        if (this.isLegSlot(slotIn)) {
//            this.modelSmallArmor = new ModelConstructArmor(0.5F, textureWidth, textureHeight, textureX, textureY);
//            return this.modelSmallArmor;
//        } else {
//            this.modelArmor = new ModelConstructArmor(1.0F, textureWidth, textureHeight, textureX, textureY + 32);
//            return this.modelArmor;
//        }
//    }

    private boolean isLegSlot(EntityEquipmentSlot slotIn)
    {
        return slotIn == EntityEquipmentSlot.LEGS;
    }

}
