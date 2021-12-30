package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryModifiers;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;

public class ArmorMaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

  public ArmorMaterialTraitsDataProvider(DataGenerator gen,
                                         AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialTraits() {
    addTraits(MaterialIds.leather, PlateMaterialStats.ID, ConstructsArmoryModifiers.PADDED.get());
    addTraits(MaterialIds.leather, MailMaterialStats.ID, ConstructsArmoryModifiers.PADDED.get());
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Traits";
  }
}
