package top.theillusivec4.constructsarmory.common;

import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.IToolStatProvider;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.library.tools.definition.PartRequirement;
import slimeknights.tconstruct.library.tools.definition.ToolDefinitionData;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.stat.ArmorStatsBuilder;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstructsArmoryDefinitions {

  public static final IToolStatProvider ARMOR_STAT_PROVIDER = new IToolStatProvider() {

    @Nonnull
    @Override
    public StatsNBT buildStats(@Nonnull ToolDefinition definition,
                               @Nonnull List<IMaterial> materials) {
      return ArmorStatsBuilder.from(definition, materials).buildStats();
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
  };

  public static final ModifiableArmorMaterial MATERIAL_ARMOR =
      ModifiableArmorMaterial.builder(ConstructsArmoryMod.getResource("material_armor"))
          .setStatsProvider(ARMOR_STAT_PROVIDER).setSoundEvent(Sounds.EQUIP_PLATE.getSound())
          .build();
}
