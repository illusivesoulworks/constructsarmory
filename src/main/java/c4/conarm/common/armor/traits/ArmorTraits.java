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

package c4.conarm.common.armor.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.monster.EntitySlime;
import slimeknights.tconstruct.world.entity.EntityBlueSlime;

public class ArmorTraits {

    public static final AbstractArmorTrait aquaspeed = new TraitAquaspeed();
    public static final AbstractArmorTrait lightweight = new TraitLightweight();
    public static final AbstractArmorTrait enderport = new TraitEnderport();
    public static final AbstractArmorTrait combustible = new TraitCombustible();
    public static final AbstractArmorTrait absorbent = new TraitAbsorbent();
    public static final AbstractArmorTrait magnetic = new TraitMagnetic(1);
    public static final AbstractArmorTrait magnetic2 = new TraitMagnetic(2);
    public static final AbstractArmorTrait tasty = new TraitTasty();
    public static final AbstractArmorTrait baconlicious = new TraitBaconlicious();
    public static final AbstractArmorTrait cheapskate = new TraitCheapskate();
    public static final AbstractArmorTrait cheap = new TraitCheap();
    public static final AbstractArmorTrait vengeful = new TraitVengeful();
    public static final AbstractArmorTrait invigorating = new TraitInvigorating();
    public static final AbstractArmorTrait slimeyGreen = new TraitSlimey("green", EntitySlime.class);
    public static final AbstractArmorTrait slimeyBlue = new TraitSlimey("blue", EntityBlueSlime.class);
    public static final AbstractArmorTrait heavy = new TraitHeavy();
    public static final AbstractArmorTrait ambitious = new TraitAmbitious();
    public static final AbstractArmorTrait blessed = new TraitBlessed();
    public static final AbstractArmorTrait spiny = new TraitSpiny();
    public static final AbstractArmorTrait mundane = new TraitMundane(1);
    public static final AbstractArmorTrait mundane2 = new TraitMundane(2);
    public static final AbstractArmorTrait aridiculous = new TraitAridiculous();
    public static final AbstractArmorTrait petravidity = new TraitPetravidity();
    public static final AbstractArmorTrait superhot = new TraitSuperhot();
    public static final AbstractArmorTrait shielding = new TraitShielding();
    public static final AbstractArmorTrait steady = new TraitSteady();
    public static final AbstractArmorTrait skeletal = new TraitSkeletal();
    public static final AbstractArmorTrait calcic = new TraitCalcic();
    public static final AbstractArmorTrait autoforge = new TraitAutoforge();
    public static final AbstractArmorTrait alien = new TraitAlien();
    public static final AbstractArmorTrait infernal = new TraitInfernal();
    public static final AbstractArmorTrait subterranean = new TraitSubterranean();
    public static final AbstractArmorTrait dramatic = new TraitDramatic();
    public static final AbstractArmorTrait indomitable = new TraitIndomitable();
    public static final AbstractArmorTrait prideful = new TraitPrideful();
    public static final AbstractArmorTrait rough = new TraitRough();
    public static final AbstractArmorTrait ecological = new TraitEcological();
    public static final AbstractArmorTrait duritae = new TraitDuritosRanch();
    public static final AbstractArmorTrait dense = new TraitDense();
    public static final AbstractArmorTrait voltaic = new TraitVoltaic();
    public static final AbstractArmorTrait bouncy = new TraitBouncy();
    public static final AbstractArmorTrait featherweight = new TraitFeatherweight();
}
