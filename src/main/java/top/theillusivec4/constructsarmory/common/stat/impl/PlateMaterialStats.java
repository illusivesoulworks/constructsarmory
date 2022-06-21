package top.theillusivec4.constructsarmory.common.stat.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.text.DecimalFormat;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import slimeknights.tconstruct.library.materials.stats.BaseMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Util;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.api.ArmorStatsCalculator;
import top.theillusivec4.constructsarmory.common.stat.ConstructsArmoryStats;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class PlateMaterialStats extends BaseMaterialStats implements IRepairableMaterialStats {

  public static final MaterialStatsId ID =
      new MaterialStatsId(ConstructsArmoryMod.getResource("plate"));
  public static final PlateMaterialStats DEFAULT = new PlateMaterialStats();

  public static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("#.##%");

  private static final List<ITextComponent> DESCRIPTION =
      ImmutableList.of(new TranslationTextComponent(
              "tool_stat." + ConstructsArmoryMod.MOD_ID + ".durability.description"),
          new TranslationTextComponent(
              "tool_stat." + ConstructsArmoryMod.MOD_ID + ".armor.description"),
          ToolStats.ARMOR_TOUGHNESS.getDescription(),
          ToolStats.KNOCKBACK_RESISTANCE.getDescription(),
          ConstructsArmoryStats.MOVEMENT_SPEED.getDescription());

  private int durability;
  private float armor;
  private float toughness;
  private float knockbackResistance;
  private float movementSpeed;

  @Override
  public void encode(PacketBuffer buffer) {
    buffer.writeInt(this.durability);
    buffer.writeFloat(this.armor);
    buffer.writeFloat(this.toughness);
    buffer.writeFloat(this.knockbackResistance);
    buffer.writeFloat(this.movementSpeed);
  }

  @Override
  public void decode(PacketBuffer buffer) {
    this.durability = buffer.readInt();
    this.armor = buffer.readFloat();
    this.toughness = buffer.readFloat();
    this.knockbackResistance = buffer.readFloat();
    this.movementSpeed = buffer.readFloat();
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
    int[] durabilities = ArmorStatsCalculator.getDurabilityStats(this.durability);
    info.add(formatArray(ToolStats.DURABILITY, durabilities[1], durabilities[3], durabilities[2],
        durabilities[0]));
    float[] armors = ArmorStatsCalculator.getArmorStats(this.armor);
    info.add(formatArray(ToolStats.ARMOR, armors[1], armors[3], armors[2], armors[0]));
    info.add(ToolStats.ARMOR_TOUGHNESS.formatValue(this.toughness));
    info.add(ToolStats.KNOCKBACK_RESISTANCE.formatValue(this.knockbackResistance * 10f));
    info.add(new TranslationTextComponent(
        "tool_stat." + ConstructsArmoryMod.MOD_ID + ".movement_speed").appendSibling(
        new StringTextComponent(PERCENT_FORMAT.format(this.movementSpeed)).modifyStyle(
            style -> style.setColor(ConstructsArmoryStats.MOVEMENT_SPEED.getColor()))));
    return info;
  }

  public ITextComponent formatArray(FloatToolStat toolStat, float num1, float num2, float num3,
                                    float num4) {
    String name = toolStat.getName().getPath();
    Color color = toolStat.getColor();
    String loc = "tool_stat." + ConstructsArmoryMod.MOD_ID + "." + name;
    return new TranslationTextComponent(loc).appendSibling(
        new StringTextComponent(Util.COMMA_FORMAT.format(num1) + "/").modifyStyle(
            style -> style.setColor(color))).appendSibling(
        new StringTextComponent(Util.COMMA_FORMAT.format(num2) + "/").modifyStyle(
            style -> style.setColor(color))).appendSibling(
        new StringTextComponent(Util.COMMA_FORMAT.format(num3) + "/").modifyStyle(
            style -> style.setColor(color))).appendSibling(
        new StringTextComponent(Util.COMMA_FORMAT.format(num4)).modifyStyle(
            style -> style.setColor(color)));
  }

  @Override
  @Nonnull
  public List<ITextComponent> getLocalizedDescriptions() {
    return DESCRIPTION;
  }
}
