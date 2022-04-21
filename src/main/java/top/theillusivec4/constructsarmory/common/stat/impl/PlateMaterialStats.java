package top.theillusivec4.constructsarmory.common.stat.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import slimeknights.tconstruct.library.materials.stats.BaseMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class PlateMaterialStats extends BaseMaterialStats implements IRepairableMaterialStats {

  public static final MaterialStatsId ID =
      new MaterialStatsId(ConstructsArmoryMod.getResource("plate"));
  public static final PlateMaterialStats DEFAULT = new PlateMaterialStats();

  private static final List<ITextComponent> DESCRIPTION =
      ImmutableList.of(ToolStats.DURABILITY.getDescription(), ToolStats.ARMOR.getDescription());

  private int durability;
  private float armor;

  @Override
  public void encode(PacketBuffer buffer) {
    buffer.writeInt(this.durability);
    buffer.writeFloat(this.armor);
  }

  @Override
  public void decode(PacketBuffer buffer) {
    this.durability = buffer.readInt();
    this.armor = buffer.readFloat();
  }

  @Nonnull
  @Override
  public MaterialStatsId getIdentifier() {
    return ID;
  }

  @Override
  @Nonnull
  public List<ITextComponent> getLocalizedInfo() {
    List<ITextComponent> info = Lists.newArrayList();
    info.add(ToolStats.DURABILITY.formatValue(this.durability));
    info.add(ToolStats.ARMOR.formatValue(this.armor));
    return info;
  }

  @Override
  @Nonnull
  public List<ITextComponent> getLocalizedDescriptions() {
    return DESCRIPTION;
  }
}
