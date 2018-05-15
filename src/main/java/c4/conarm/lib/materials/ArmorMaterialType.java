/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

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
