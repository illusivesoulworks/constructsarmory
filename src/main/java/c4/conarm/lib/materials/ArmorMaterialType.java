package c4.conarm.lib.materials;

import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;

public class ArmorMaterialType {

    public static String CORE = "core";
    public static String PLATES = "plates";
    public static String TRIM = "trim";

    public static PartMaterialType core(IToolPart part) {
        return new PartMaterialType(part, CORE);
    }

    public static PartMaterialType trim(IToolPart part) {
        return new PartMaterialType(part, TRIM);
    }

    public static PartMaterialType plating(IToolPart part) {

        return new PartMaterialType(part, PLATES);
    }
}
