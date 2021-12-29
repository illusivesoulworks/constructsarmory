package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class ArmorMaterialDataProvider extends AbstractMaterialDataProvider {

  public ArmorMaterialDataProvider(DataGenerator gen) {
    super(gen);
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Materials";
  }

  @Override
  protected void addMaterials() {
    // Nothing currently
  }
}
