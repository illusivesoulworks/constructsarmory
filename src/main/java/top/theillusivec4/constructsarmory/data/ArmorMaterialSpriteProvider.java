package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;

public class ArmorMaterialSpriteProvider extends AbstractMaterialSpriteProvider {

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Material Textures";
  }

  @Override
  protected void addAllMaterials() {
    buildArmorMaterial(MaterialIds.cobalt)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF001944).addARGB(102, 0xFF00296D)
                .addARGB(140, 0xFF0043A5).addARGB(178, 0xFF186ACE).addARGB(216, 0xFF338FEA)
                .addARGB(255, 0xFF59A6EF).build());
    buildArmorMaterial(MaterialIds.manyullyn)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF350C57).addARGB(102, 0xFF481D6D)
                .addARGB(140, 0xFF5C268A).addARGB(178, 0xFF7141AC).addARGB(216, 0xFF9261CC)
                .addARGB(255, 0xFFC299F3).build());
  }

  protected MaterialSpriteInfoBuilder buildArmorMaterial(ResourceLocation name) {
    return buildMaterial(name).statType(ArmorMaterialStatsIdentifiers.PLATE)
        .statType(ArmorMaterialStatsIdentifiers.MAIL);
  }
}
