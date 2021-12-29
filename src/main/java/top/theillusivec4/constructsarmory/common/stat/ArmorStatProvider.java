package top.theillusivec4.constructsarmory.common.stat;

import java.util.List;
import javax.annotation.Nonnull;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.IToolStatProvider;
import slimeknights.tconstruct.library.tools.definition.PartRequirement;
import slimeknights.tconstruct.library.tools.definition.ToolDefinitionData;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

public class ArmorStatProvider implements IToolStatProvider {

  private final ArmorSlotType slotType;

  public ArmorStatProvider(ArmorSlotType slotType) {
    this.slotType = slotType;
  }

  @Nonnull
  @Override
  public StatsNBT buildStats(@Nonnull ToolDefinition definition,
                             @Nonnull List<IMaterial> materials) {
    return ArmorStatsBuilder.from(this.slotType, definition, materials).buildStats();
  }

  @Override
  public boolean isMultipart() {
    return true;
  }

  @Override
  public void validate(ToolDefinitionData data) {
    List<PartRequirement> requirements = data.getParts();

    if (requirements.isEmpty()) {
      throw new IllegalStateException("Must have at least one armor part for an armor piece");
    }
    boolean foundPlate = false;

    for (PartRequirement req : requirements) {
      MaterialStatsId statType = req.getStatType();

      if (statType.equals(PlateMaterialStats.ID)) {
        foundPlate = true;
      } else if (!statType.equals(MailMaterialStats.ID) &&
          !statType.equals(TrimMaterialStats.ID)) {
        throw new IllegalStateException(
            "Invalid armor part type, only support plate, mail, and trim part types");
      }
    }

    if (!foundPlate) {
      throw new IllegalStateException("Armor piece must use at least one plate part");
    }
  }
}
