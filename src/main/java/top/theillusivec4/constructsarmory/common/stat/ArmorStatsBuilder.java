package top.theillusivec4.constructsarmory.common.stat;

import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.PartRequirement;
import slimeknights.tconstruct.library.tools.definition.ToolDefinitionData;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.tools.stat.ToolStatsBuilder;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

@Getter(AccessLevel.PROTECTED)
public final class ArmorStatsBuilder extends ToolStatsBuilder {

  private final List<PlateMaterialStats> plates;
  private final List<MailMaterialStats> mail;
  private final List<TrimMaterialStats> trims;

  @VisibleForTesting
  public ArmorStatsBuilder(ToolDefinitionData toolData, List<PlateMaterialStats> plates,
                           List<MailMaterialStats> mail,
                           List<TrimMaterialStats> trims) {
    super(toolData);
    this.plates = plates;
    this.mail = mail;
    this.trims = trims;
  }

  public static ToolStatsBuilder from(ToolDefinition toolDefinition, List<IMaterial> materials) {
    ToolDefinitionData data = toolDefinition.getData();
    List<PartRequirement> requiredComponents = data.getParts();
    // if the NBT is invalid, at least we can return the default stats builder, as an exception here could kill itemstacks
    if (materials.size() != requiredComponents.size()) {
      return ToolStatsBuilder.noParts(toolDefinition);
    }
    return new ArmorStatsBuilder(data,
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.PLATE, materials, requiredComponents),
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.MAIL, materials, requiredComponents),
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.TRIM, materials, requiredComponents));
  }

  @Override
  protected void setStats(StatsNBT.Builder builder) {
    builder.set(ToolStats.DURABILITY, buildDurability());
    builder.set(ToolStats.ARMOR, buildArmor());
    builder.set(ToolStats.ARMOR_TOUGHNESS, buildArmorToughness());
    builder.set(ToolStats.KNOCKBACK_RESISTANCE, buildKnockbackResistance());
  }

  @Override
  protected boolean handles(@Nonnull IToolStat<?> stat) {
    return stat == ToolStats.DURABILITY || stat == ToolStats.ARMOR ||
        stat == ToolStats.ARMOR_TOUGHNESS || stat == ToolStats.KNOCKBACK_RESISTANCE;
  }

  public float buildDurability() {
    double averagePlateDurability =
        getAverageValue(this.plates, PlateMaterialStats::getDurability) +
            this.toolData.getBonus(ToolStats.DURABILITY);
    double averageMailModifier = getAverageValue(this.mail, MailMaterialStats::getDurability, 1);
    return Math.max(1, (int) (averagePlateDurability * averageMailModifier));
  }

  public float buildArmor() {
    double averageArmor = getAverageValue(this.plates, PlateMaterialStats::getArmor) +
        this.toolData.getBonus(ToolStats.ARMOR);
    double averageMailModifier = getAverageValue(this.mail, MailMaterialStats::getArmor, 1);
    return (float) Math.max(0, averageArmor * averageMailModifier);
  }

  public float buildArmorToughness() {
    double averageToughness = getAverageValue(this.plates, PlateMaterialStats::getArmorToughness) +
        this.toolData.getBonus(ToolStats.ARMOR_TOUGHNESS);
    double averageMailModifier =
        getAverageValue(this.mail, MailMaterialStats::getArmorToughness, 1);
    return (float) Math.max(0, averageToughness * averageMailModifier);
  }

  public float buildKnockbackResistance() {
    double averageKnockbackResistance =
        getAverageValue(this.plates, PlateMaterialStats::getKnockbackResistance) +
            this.toolData.getBonus(ToolStats.KNOCKBACK_RESISTANCE);
    return (float) Math.max(0, averageKnockbackResistance);
  }
}
