/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.constructsarmory.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.CultivatedModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.DenseModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.OvercastModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.OvergrowthModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.OverlordModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.OverworkedModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.SolarPoweredModifier;
import slimeknights.tconstruct.tools.modifiers.traits.general.SturdyModifier;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.HallowedModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.IgneousModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.PetrousModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.StableModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.VengefulModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.WeightyModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.BlightedModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.BloodlettingModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.CounterattackModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.EnkindlingModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.MalignantModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.PricklyModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.battle.counter.SplinteredModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.general.DuctileModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.general.EndershieldModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.general.ExperiencedModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.general.SavoryModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.general.ShieldingModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.general.StoneguardModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.AccelerationModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.AerialModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.DelvingModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.FerventModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.ImmaculateModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.ImmaculateModifier2;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.NimbleModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.RadiantModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.SalvagedModifier;
import com.illusivesoulworks.constructsarmory.common.modifier.trait.speed.WovenModifier;

public class ConstructsArmoryModifiers {

  private static final DeferredRegister<Modifier>
      MODIFIERS = DeferredRegister.create(Modifier.class, ConstructsArmoryMod.MOD_ID);

  // Tier 1
  public static final RegistryObject<CultivatedModifier> CULTIVATED =
      MODIFIERS.register("cultivated", CultivatedModifier::new);
  public static final RegistryObject<WovenModifier> WOVEN =
      MODIFIERS.register("woven", WovenModifier::new);
  public static final RegistryObject<PetrousModifier> PETROUS =
      MODIFIERS.register("petrous", PetrousModifier::new);
  public static final RegistryObject<CounterattackModifier> SPLINTERED =
      MODIFIERS.register("splintered", SplinteredModifier::new);
  public static final RegistryObject<SolarPoweredModifier> SOLAR_POWERED =
      MODIFIERS.register("solar_powered", SolarPoweredModifier::new);

  // Tier 2
  public static final RegistryObject<SturdyModifier> STURDY =
      MODIFIERS.register("sturdy", SturdyModifier::new);
  public static final RegistryObject<DelvingModifier> DELVING =
      MODIFIERS.register("delving", DelvingModifier::new);
  public static final RegistryObject<OvergrowthModifier> OVERGROWTH =
      MODIFIERS.register("overgrowth", OvergrowthModifier::new);
  public static final RegistryObject<IgneousModifier> IGNEOUS =
      MODIFIERS.register("igneous", IgneousModifier::new);
  public static final RegistryObject<AerialModifier> AERIAL =
      MODIFIERS.register("aerial", AerialModifier::new);
  public static final RegistryObject<BloodlettingModifier> BLOODLETTING =
      MODIFIERS.register("bloodletting", BloodlettingModifier::new);
  public static final RegistryObject<MalignantModifier> MALIGNANT =
      MODIFIERS.register("malignant", MalignantModifier::new);

  // Tier 2 Addons
  public static final RegistryObject<DenseModifier> DENSE =
      MODIFIERS.register("dense", DenseModifier::new);
  public static final RegistryObject<WeightyModifier> WEIGHTY =
      MODIFIERS.register("weighty", WeightyModifier::new);
  public static final RegistryObject<RadiantModifier> RADIANT =
      MODIFIERS.register("radiant", RadiantModifier::new);
  public static final RegistryObject<HallowedModifier> HALLOWED =
      MODIFIERS.register("hallowed", HallowedModifier::new);
  public static final RegistryObject<ShieldingModifier> SHIELDING =
      MODIFIERS.register("shielding", ShieldingModifier::new);
  public static final RegistryObject<StoneguardModifier> STONEGUARD =
      MODIFIERS.register("stoneguard", StoneguardModifier::new);

  // Tier 3
  public static final RegistryObject<OvercastModifier> OVERCAST =
      MODIFIERS.register("overcast", OvercastModifier::new);
  public static final RegistryObject<ImmaculateModifier> IMMACULATE =
      MODIFIERS.register("immaculate", ImmaculateModifier::new);
  public static final RegistryObject<NimbleModifier> NIMBLE =
      MODIFIERS.register("nimble", NimbleModifier::new);
  public static final RegistryObject<PricklyModifier> PRICKLY =
      MODIFIERS.register("prickly", PricklyModifier::new);
  public static final RegistryObject<SavoryModifier> SAVORY =
      MODIFIERS.register("savory", SavoryModifier::new);

  // Tier 3 Addons
  public static final RegistryObject<DuctileModifier> DUCTILE =
      MODIFIERS.register("ductile", DuctileModifier::new);
  public static final RegistryObject<ImmaculateModifier2> IMMACULATE2 =
      MODIFIERS.register("immaculate_2", ImmaculateModifier2::new);
  public static final RegistryObject<StableModifier> STABLE =
      MODIFIERS.register("stable", StableModifier::new);
  public static final RegistryObject<FerventModifier> FERVENT =
      MODIFIERS.register("fervent", FerventModifier::new);
  public static final RegistryObject<ExperiencedModifier> EXPERIENCED =
      MODIFIERS.register("experienced", ExperiencedModifier::new);
  public static final RegistryObject<CounterattackModifier> BLIGHTED =
      MODIFIERS.register("blighted", BlightedModifier::new);
  public static final RegistryObject<OverworkedModifier> OVERWORKED =
      MODIFIERS.register("overworked", OverworkedModifier::new);

  // Tier 4
  public static final RegistryObject<OverlordModifier> OVERLORD =
      MODIFIERS.register("overlord", OverlordModifier::new);
  public static final RegistryObject<SalvagedModifier> SALVAGED =
      MODIFIERS.register("salvaged", SalvagedModifier::new);
  public static final RegistryObject<AccelerationModifier> ACCELERATION =
      MODIFIERS.register("acceleration", AccelerationModifier::new);
  public static final RegistryObject<VengefulModifier> VENGEFUL =
      MODIFIERS.register("vengeful", VengefulModifier::new);
  public static final RegistryObject<CounterattackModifier> ENKINDLING =
      MODIFIERS.register("enkindling", EnkindlingModifier::new);

  // Tier 5
  public static final RegistryObject<EndershieldModifier> ENDERSHIELD =
      MODIFIERS.register("endershield", EndershieldModifier::new);

  public static void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    MODIFIERS.register(bus);
  }
}
