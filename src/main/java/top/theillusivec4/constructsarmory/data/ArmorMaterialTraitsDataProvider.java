package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.Modifier;
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
    // Tier 1
    addArmorTraits(MaterialIds.stone, ConstructsArmoryModifiers.PETROUS.get());
    addArmorTraits(MaterialIds.leather, ConstructsArmoryModifiers.WOVEN.get());
    addArmorTraits(MaterialIds.bone, ConstructsArmoryModifiers.SPLINTERED.get());

    // Tier 2
    addArmorTraits(MaterialIds.copper, ConstructsArmoryModifiers.DELVING.get());
    addTraits(MaterialIds.skyslimeVine, MailMaterialStats.ID,
        ConstructsArmoryModifiers.AERIAL.get());

    // Tier 3
    addArmorTraits(MaterialIds.necronium, ConstructsArmoryModifiers.BLIGHTED.get());

    // Tier 4
    addArmorTraits(MaterialIds.blazingBone, ConstructsArmoryModifiers.ENKINDLING.get());
  }

  protected void addArmorTraits(MaterialId materialId, Modifier trait) {
    addTraits(materialId, PlateMaterialStats.ID, trait);
    addTraits(materialId, MailMaterialStats.ID, trait);
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Traits";
  }
}
