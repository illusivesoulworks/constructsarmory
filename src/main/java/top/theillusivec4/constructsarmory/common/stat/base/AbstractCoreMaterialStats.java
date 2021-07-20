package top.theillusivec4.constructsarmory.common.stat.base;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.library.materials.stats.BaseMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import top.theillusivec4.constructsarmory.api.ArmorStats;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public abstract class AbstractCoreMaterialStats extends BaseMaterialStats
    implements IRepairableMaterialStats {

  private static final List<ITextComponent> DESCRIPTION = ImmutableList
      .of(ToolStats.DURABILITY.getDescription(), ArmorStats.ARMOR.getDescription(),
          ArmorStats.TOUGHNESS.getDescription());

  public static final Color DURABILITY_COLOR = Color.fromInt(0xFF47cc47);
  public static final Color MINING_SPEED_COLOR = Color.fromInt(0xFF78A0CD);
  public static final Color ATTACK_COLOR = Color.fromInt(0xFFD76464);

  private MaterialStatsId identifier;
  private int durability;
  private float armor;
  private float toughness;

  public AbstractCoreMaterialStats(MaterialStatsId identifier, float durability, float durabilityMultiplier, float armor, float toughness) {
    this.identifier = identifier;
    this.durability = (int) Math.max(1, Math.floor(durability * durabilityMultiplier));
    this.armor = armor;
    this.toughness = toughness;
  }

  @Override
  public void encode(PacketBuffer buffer) {
    buffer.writeInt(this.durability);
    buffer.writeFloat(this.armor);
    buffer.writeFloat(this.toughness);
  }

  @Override
  public void decode(PacketBuffer buffer) {
    this.durability = buffer.readInt();
    this.armor = buffer.readFloat();
    this.toughness = buffer.readFloat();
  }

  @Override
  @Nonnull
  public List<ITextComponent> getLocalizedInfo() {
    List<ITextComponent> info = Lists.newArrayList();
    info.add(ToolStats.DURABILITY.formatValue(this.durability));
    info.add(ArmorStats.ARMOR.formatValue(this.armor));
    info.add(ArmorStats.TOUGHNESS.formatValue(this.toughness));
    return info;
  }

  @Override
  @Nonnull
  public List<ITextComponent> getLocalizedDescriptions() {
    return DESCRIPTION;
  }
}
