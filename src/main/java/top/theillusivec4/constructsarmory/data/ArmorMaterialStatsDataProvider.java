package top.theillusivec4.constructsarmory.data;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

public class ArmorMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {

  public ArmorMaterialStatsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialStats() {
    addMaterialStats(MaterialIds.wood, 56, 4.0f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.flint, 80, 6.5f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.stone, 136, 7.0f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.bone, 104, 6.0f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.necroticBone, 144, 5.5f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.iron, 224, 15.0f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.searedStone, 160, 13.0f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.scorchedStone, 144, 11.0f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.copper, 200, 15.0f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.slimewood, 304, 9.0f, 2.0f, 0.0f);
    addMaterialStats(MaterialIds.roseGold, 144, 20.0f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.silver, 288, 13.0f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.lead, 192, 12.0f, 1, 0.15f);
    addMaterialStats(MaterialIds.electrum, 208, 16.0f, 0.0f, 0.0f);
    addMaterialStats(MaterialIds.slimesteel, 384, 13.0f, 3.0f, 0.0f);
    addMaterialStats(MaterialIds.nahuatl, 288, 16.0f, 2.0f, 0.0f);
    addMaterialStats(MaterialIds.tinkersBronze, 320, 18.0f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.pigIron, 320, 17.0f, 2.0f, 0.0f);
    addMaterialStats(MaterialIds.steel, 352, 19.0f, 2.0f, 0.0f);
    addMaterialStats(MaterialIds.bronze, 344, 18.5f, 1.0f, 0.0f);
    addMaterialStats(MaterialIds.constantan, 336, 18.0f, 2.0f, 0.0f);
    addMaterialStats(MaterialIds.cobalt, 352, 20.0f, 2.0f, 0.0f);
    addMaterialStats(MaterialIds.queensSlime, 560, 18.0f, 3.0f, 0.0f);
    addMaterialStats(MaterialIds.manyullyn, 528, 20.0f, 4.0f, 0.0f);
    addMaterialStats(MaterialIds.hepatizon, 512, 24.0f, 2.0f, 0.0f);
  }

  protected void addMaterialStats(MaterialId id, int durability, float armor, float toughness,
                                  float knockbackResistance) {
    addMaterialStats(id, new PlateMaterialStats(durability, armor, toughness, knockbackResistance),
        new MailMaterialStats(), new TrimMaterialStats());
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Stats";
  }
}
