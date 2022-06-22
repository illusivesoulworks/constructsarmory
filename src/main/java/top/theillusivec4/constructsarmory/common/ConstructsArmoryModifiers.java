package top.theillusivec4.constructsarmory.common;

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
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.HallowedModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.IgneousModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.PetrousModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.StableModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.VengefulModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.WeightyModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.BlightedModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.BloodlettingModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.CounterattackModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.EnkindlingModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.MalignantModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.PricklyModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.battle.counter.SplinteredModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.general.DuctileModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.general.EndershieldModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.general.ExperiencedModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.general.SavoryModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.general.ShieldingModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.general.StoneguardModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.AccelerationModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.AerialModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.DelvingModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.FerventModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.ImmaculateModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.ImmaculateModifier2;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.NimbleModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.RadiantModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.SalvagedModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.speed.WovenModifier;

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
