package top.theillusivec4.constructsarmory.common.stat;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.inventory.EquipmentSlotType;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.ToolBaseStatDefinition;
import slimeknights.tconstruct.library.tools.ToolDefinition;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.part.IToolPart;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.tools.stat.ToolStatsBuilder;
import top.theillusivec4.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import top.theillusivec4.constructsarmory.api.ArmorStats;
import top.theillusivec4.constructsarmory.common.stat.base.AbstractCoreMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.PlateMaterialStats;
import top.theillusivec4.constructsarmory.common.stat.impl.TrimMaterialStats;

@Getter(AccessLevel.PROTECTED)
public final class ArmorStatsBuilder extends ToolStatsBuilder {

  private static final Map<EquipmentSlotType, MaterialStatsId> SLOT_TYPE =
      new EnumMap<>(EquipmentSlotType.class);

  static {
    SLOT_TYPE.put(EquipmentSlotType.HEAD, ArmorMaterialStatsIdentifiers.HELMET_CORE);
    SLOT_TYPE.put(EquipmentSlotType.CHEST, ArmorMaterialStatsIdentifiers.CHESTPLATE_CORE);
    SLOT_TYPE.put(EquipmentSlotType.LEGS, ArmorMaterialStatsIdentifiers.LEGGINGS_CORE);
    SLOT_TYPE.put(EquipmentSlotType.FEET, ArmorMaterialStatsIdentifiers.BOOTS_CORE);
  }

  private final List<AbstractCoreMaterialStats> cores;
  private final List<PlateMaterialStats> plates;
  private final List<TrimMaterialStats> trims;

  public ArmorStatsBuilder(ToolBaseStatDefinition baseStats, List<AbstractCoreMaterialStats> cores,
                           List<PlateMaterialStats> plates, List<TrimMaterialStats> trims) {
    super(baseStats);
    this.cores = cores;
    this.plates = plates;
    this.trims = trims;
  }

  public static ToolStatsBuilder from(ToolDefinition toolDefinition, List<IMaterial> materials,
                                      EquipmentSlotType slotType) {
    List<IToolPart> requiredComponents = toolDefinition.getRequiredComponents();

    if (materials.size() != requiredComponents.size()) {
      return ToolStatsBuilder.noParts(toolDefinition);
    }
    ToolBaseStatDefinition baseStats = toolDefinition.getBaseStatDefinition();
    List<AbstractCoreMaterialStats> coreStats =
        listOfCompatibleWith(SLOT_TYPE.get(slotType), materials, requiredComponents);
    int primaryWeight = baseStats.getPrimaryHeadWeight();

    if (primaryWeight > 1 && coreStats.size() > 1) {

      for (int i = 1; i < primaryWeight; i++) {
        coreStats.add(coreStats.get(0));
      }
    }
    return new ArmorStatsBuilder(baseStats, coreStats,
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.PLATE, materials, requiredComponents),
        listOfCompatibleWith(ArmorMaterialStatsIdentifiers.TRIM, materials, requiredComponents)
    );
  }

  @Override
  protected void setStats(StatsNBT.Builder builder) {
    builder.set(ToolStats.DURABILITY, buildDurability());
    builder.set(ArmorStats.ARMOR, buildArmor());
    builder.set(ArmorStats.TOUGHNESS, buildToughness());
  }

  @Override
  protected boolean handles(@Nonnull IToolStat<?> stat) {
    return stat == ToolStats.DURABILITY || stat == ArmorStats.ARMOR || stat == ArmorStats.TOUGHNESS;
  }

  public float buildDurability() {
    double averageCoreDurability =
        getAverageValue(cores, AbstractCoreMaterialStats::getDurability) +
            baseStats.getBonus(ToolStats.DURABILITY);
    double averagePlateModifier = getAverageValue(plates, PlateMaterialStats::getDurability, 1);
    return Math.max(1, (int) (averageCoreDurability * averagePlateModifier));
  }

  public float buildArmor() {
    double averageArmor = getAverageValue(cores, AbstractCoreMaterialStats::getArmor) +
        baseStats.getBonus(ArmorStats.ARMOR);
    double averagePlateModifier = getAverageValue(plates, PlateMaterialStats::getArmor, 1);
    return (float) Math.max(0, averageArmor * averagePlateModifier);
  }

  public float buildToughness() {
    double averageToughness = getAverageValue(cores, AbstractCoreMaterialStats::getToughness) +
        baseStats.getBonus(ArmorStats.TOUGHNESS);
    double averagePlateModifier = getAverageValue(plates, PlateMaterialStats::getToughness, 1);
    return (float) Math.max(0, averageToughness * averagePlateModifier);
  }
}
