package top.theillusivec4.constructsarmory.data;

import static slimeknights.tconstruct.library.utils.HarvestLevels.DIAMOND;
import static slimeknights.tconstruct.library.utils.HarvestLevels.IRON;
import static slimeknights.tconstruct.library.utils.HarvestLevels.NETHERITE;
import static slimeknights.tconstruct.library.utils.HarvestLevels.STONE;
import static slimeknights.tconstruct.library.utils.HarvestLevels.WOOD;

import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.MaterialIds;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import top.theillusivec4.constructsarmory.common.stats.impl.BootsCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stats.impl.ChestplateCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stats.impl.HelmetCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stats.impl.LeggingsCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stats.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stats.impl.TrimMaterialStats;

public class ArmorMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {

  public ArmorMaterialStatsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
    super(gen, materials);
  }

  @Override
  protected void addMaterialStats() {
    addMaterialStats(MaterialIds.wood, 3.5f, 4.0f, 0.0f);
    addMaterialStats(MaterialIds.flint, 5.0f, 6.5f, 0.0f);
    addMaterialStats(MaterialIds.stone, 8.5f, 7.0f, 0.0f);
    addMaterialStats(MaterialIds.bone, 6.5f, 6.0f, 1.0f);
    addMaterialStats(MaterialIds.necroticBone, 9.0f, 5.5f, 1.0f);
    addMaterialStats(MaterialIds.iron, 14.0f, 15.0f, 0.0f);
    addMaterialStats(MaterialIds.searedStone, 10.0f, 13.0f, 1.0f);
    addMaterialStats(MaterialIds.scorchedStone, 9.0f, 11.0f, 1.0f);
    addMaterialStats(MaterialIds.copper, 12.5f, 15.0f, 0.0f);
    addMaterialStats(MaterialIds.slimewood, 19.0f, 9.0f, 2.0f);
    addMaterialStats(MaterialIds.roseGold, 9.0f, 20.0f, 0.0f);
    addMaterialStats(MaterialIds.silver, 18.0f, 13.0f, 1.0f);
    addMaterialStats(MaterialIds.lead, 12.0f, 12.0f, 1);
    addMaterialStats(MaterialIds.electrum, 13.0f, 16.0f, 0.0f);
    addMaterialStats(MaterialIds.slimesteel, 24.0f, 13.0f, 3.0f);
    addMaterialStats(MaterialIds.nahuatl, 18.0f, 16.0f, 2.0f);
    addMaterialStats(MaterialIds.tinkersBronze, 20.0f, 18.0f, 1.0f);
    addMaterialStats(MaterialIds.pigIron, 20.0f, 17.0f, 2.0f);
    addMaterialStats(MaterialIds.steel, 22.0f, 19.0f, 2.0f);
    addMaterialStats(MaterialIds.bronze, 21.5f, 18.5f, 1.0f);
    addMaterialStats(MaterialIds.constantan, 21.0f, 18.0f, 2.0f);
    addMaterialStats(MaterialIds.cobalt, 22.0f, 20.0f, 2.0f);
    addMaterialStats(MaterialIds.queensSlime, 35.0f, 18.0f, 3.0f);
    addMaterialStats(MaterialIds.manyullyn, 33.0f, 20.0f, 4.0f);
    addMaterialStats(MaterialIds.hepatizon, 32.0f, 24.0f, 2.0f);
  }

  protected void addMaterialStats(MaterialId id, float durability, float armor, float toughness) {
    addMaterialStats(id, new HelmetCoreMaterialStats(durability, armor, toughness),
        new ChestplateCoreMaterialStats(durability, armor, toughness),
        new LeggingsCoreMaterialStats(durability, armor, toughness),
        new BootsCoreMaterialStats(durability, armor, toughness),
        new PlateMaterialStats(), new TrimMaterialStats());
  }

  @Override
  @Nonnull
  public String getName() {
    return "Construct's Armory Material Stats";
  }
}
