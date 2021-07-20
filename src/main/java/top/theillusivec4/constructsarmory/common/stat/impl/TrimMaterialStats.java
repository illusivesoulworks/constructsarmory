package top.theillusivec4.constructsarmory.common.stat.impl;

import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class TrimMaterialStats extends ExtraMaterialStats {

  public static final TrimMaterialStats DEFAULT = new TrimMaterialStats();

  @Override
  @Nonnull
  public MaterialStatsId getIdentifier() {
    return ArmorMaterialStatsIdentifiers.TRIM;
  }
}
