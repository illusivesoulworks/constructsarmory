package c4.conarm.lib.materials;

import com.google.common.collect.Lists;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;

import java.util.List;

public class CoreMaterialStats extends AbstractMaterialStats {

    public final static String LOC_Durability = "stat.core.durability.name";
    public final static String LOC_Defense = "stat.core.defense.name";

    public final static String LOC_DurabilityDesc = "stat.core.durability.desc";
    public final static String LOC_DefenseDesc = "stat.core.defense.desc";

    public final static String COLOR_Durability = CustomFontColor.valueToColorCode(1f);
    public final static String COLOR_Defense = CustomFontColor.encodeColor(215, 100, 100);

    public final float durability;
    public final float defense;

    public CoreMaterialStats(float durability, float defense) {
        super(ArmorMaterialType.CORE);
        this.durability = durability;
        this.defense = defense;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();

        info.add(formatDurability(durability));
        info.add(formatDefense(defense));

        return info;
    }

    public static String formatDurability(float durability) {
        return formatNumber(LOC_Durability, COLOR_Durability, durability);
    }

//    public static String formatDurability(int durability, int ref) {
//        return String.format("%s: %s",
//                Util.translate(LOC_Durability),
//                CustomFontColor.formatPartialAmount(durability, ref))
//                + TextFormatting.RESET;
//    }

    public static String formatDefense(float defense) {
        return formatNumber(LOC_Defense, COLOR_Defense, defense);
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_DurabilityDesc));
        info.add(Util.translate(LOC_DefenseDesc));

        return info;
    }
}
