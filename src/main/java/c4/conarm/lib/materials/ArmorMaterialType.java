/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
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
