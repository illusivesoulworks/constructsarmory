package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class ArmorMaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

  public ArmorMaterialTraitsDataProvider(DataGenerator gen,
                                         AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialTraits() {

  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Traits";
  }
}
