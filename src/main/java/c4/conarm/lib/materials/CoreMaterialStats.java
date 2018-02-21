package c4.conarm.lib.materials;

import c4.conarm.armor.ArmorHelper;
import com.google.common.collect.Lists;
import net.minecraft.inventory.EntityEquipmentSlot;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;

import java.util.List;

public class CoreMaterialStats extends AbstractMaterialStats {

    public final static String LOC_Durability = "stat.core.durability.name";
    public final static String LOC_Armor = "stat.core.armor.name";
    public final static String LOC_Core = "stat.core.armor.";

    public final static String LOC_DurabilityDesc = "stat.core.durability.desc";
    public final static String LOC_ArmorDesc = "stat.core.armor.desc";

    public final static String COLOR_Durability = CustomFontColor.valueToColorCode(1f);
    public final static String COLOR_Armor = CustomFontColor.encodeColor(215, 100, 100);

    public final float durability;
    public final float[] armor;

    public CoreMaterialStats(float durability, float feet, float legs, float chest, float head) {
        super(ArmorMaterialType.CORE);
        this.durability = durability;
        this.armor = new float[4];
        this.armor[0] = feet;
        this.armor[1] = legs;
        this.armor[2] = chest;
        this.armor[3] = head;
    }

    public List<String> getLocalizedInfo(EntityEquipmentSlot slotIn) {
        List<String> info = Lists.newArrayList();
        info.add(formatDurability(durability));
        info.add(formatArmor(armor[slotIn.getIndex()]));
        return info;
    }

    @Override
    public List<String> getLocalizedInfo() {
        List<String> info = Lists.newArrayList();
        info.add(formatDurability(durability));
        info.add(String.format("%s: ", Util.translate(LOC_Armor)));
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if (slot == EntityEquipmentSlot.OFFHAND || slot == EntityEquipmentSlot.MAINHAND) {
                continue;
            }
            info.add(String.format("%s: %s%s", Util.translate(LOC_Core + slot.getName()), COLOR_Armor, armor[slot.getIndex()]));
        }
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

    public static String formatArmor(float armor) {
        return formatNumber(LOC_Armor, COLOR_Armor, armor);
    }

    @Override
    public List<String> getLocalizedDesc() {
        List<String> info = Lists.newArrayList();

        info.add(Util.translate(LOC_DurabilityDesc));
        info.add(Util.translate(LOC_ArmorDesc));
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if (slot == EntityEquipmentSlot.OFFHAND || slot == EntityEquipmentSlot.MAINHAND) {
                continue;
            }
            info.add(Util.translate(LOC_ArmorDesc));
        }

        return info;
    }
}
