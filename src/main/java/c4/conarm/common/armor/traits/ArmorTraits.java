/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.traits;

import c4.conarm.common.armor.traits.*;
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
