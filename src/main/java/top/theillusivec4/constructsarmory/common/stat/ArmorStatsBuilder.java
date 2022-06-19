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
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.api.ArmorStatsCalculator;
import top.theillusivec4.constructsarmory.common.stat.impl.MailMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;

@Getter(AccessLevel.PROTECTED)
public final class ArmorStatsBuilder extends ToolStatsBuilder {

  private final ArmorSlotType slotType;
  private final List<PlateMaterialStats> plates;
  private final List<MailMaterialStats> mail;

  @VisibleForTesting
  public ArmorStatsBuilder(ArmorSlotType slotType, ToolDefinitionData toolData,
                           List<PlateMaterialStats> plates, List<MailMaterialStats> mail) {
    super(toolData);
    this.slotType = slotType;
    this.plates = plates;
    this.mail = mail;
  }

  public static ToolStatsBuilder from(ArmorSlotType slotType, ToolDefinition toolDefinition,
                                      List<IMaterial> materials) {
    ToolDefinitionData data = toolDefinition.getData();
    List<PartRequirement> requiredComponents = data.getParts();

    if (materials.size() != requiredComponents.size()) {
      return ToolStatsBuilder.noParts(toolDefinition);
    }
    return new ArmorStatsBuilder(slotType, data,
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.PLATE, materials, requiredComponents),
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.MAIL, materials, requiredComponents));
  }

  @Override
  protected void setStats(StatsNBT.Builder builder) {
    builder.set(ToolStats.DURABILITY, buildDurability());
    builder.set(ToolStats.ARMOR, buildArmor());
    builder.set(ToolStats.ARMOR_TOUGHNESS, buildArmorToughness());
    builder.set(ToolStats.KNOCKBACK_RESISTANCE, buildKnockbackResistance());
    builder.set(ConstructsArmoryStats.MOVEMENT_SPEED, buildMovementSpeed());
  }

  @Override
  protected boolean handles(@Nonnull IToolStat<?> stat) {
    return stat == ToolStats.DURABILITY || stat == ToolStats.ARMOR ||
        stat == ToolStats.ARMOR_TOUGHNESS || stat == ToolStats.KNOCKBACK_RESISTANCE ||
        stat == ConstructsArmoryStats.MOVEMENT_SPEED;
  }

  public float buildDurability() {
    double averagePlateDurability =
        getAverageValue(this.plates, PlateMaterialStats::getDurability) +
            this.toolData.getBonus(ToolStats.DURABILITY);
    double averageMailModifier = getAverageValue(this.mail, MailMaterialStats::getDurability, 1);
    return Math.max(1,
        (int) (ArmorStatsCalculator.getDurabilityStat((int) averagePlateDurability, this.slotType) *
            averageMailModifier));
  }

  public float buildArmor() {
    double averageArmor = getAverageValue(this.plates, PlateMaterialStats::getArmor) +
        this.toolData.getBonus(ToolStats.ARMOR);
    double averageMailModifier = getAverageValue(this.mail, MailMaterialStats::getArmor, 0);
    return (float) (ArmorStatsCalculator.getArmorStat((int) averageArmor, this.slotType) *
        averageMailModifier);
  }

  public float buildArmorToughness() {
    double averageToughness = getAverageValue(this.plates, PlateMaterialStats::getToughness, 0) +
        this.toolData.getBonus(ToolStats.ARMOR_TOUGHNESS);
    return (float) Math.max(0, averageToughness);
  }

  public float buildKnockbackResistance() {
    double averageKnockbackResistance =
        getAverageValue(this.plates, PlateMaterialStats::getKnockbackResistance, 0) +
            this.toolData.getBonus(ToolStats.KNOCKBACK_RESISTANCE);
    return (float) Math.max(0, averageKnockbackResistance);
  }

  private float buildMovementSpeed() {
    double averageMovementSpeed =
        getAverageValue(this.plates, PlateMaterialStats::getMovementSpeed, 0) +
            this.toolData.getBonus(ConstructsArmoryStats.MOVEMENT_SPEED);
    double averageMailModifier = getAverageValue(this.mail, MailMaterialStats::getMovementSpeed, 1);
    return Math.round((float) Math.max(0, averageMovementSpeed * averageMailModifier) * 100f) /
        100f;
  }
}
