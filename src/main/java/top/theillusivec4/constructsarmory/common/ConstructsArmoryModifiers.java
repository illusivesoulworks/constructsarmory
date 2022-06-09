package top.theillusivec4.constructsarmory.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.library.modifiers.Modifier;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.modifier.trait.AerialModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.ArmorSpeedTradeModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.BlightedModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.CounterattackModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.DelvingModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.EnkindlingModifier;
import top.theillusivec4.constructsarmory.common.modifier.trait.SplinteredModifier;

public class ConstructsArmoryModifiers {

  private static final DeferredRegister<Modifier>
      MODIFIERS = DeferredRegister.create(Modifier.class, ConstructsArmoryMod.MOD_ID);

  public static final RegistryObject<ArmorSpeedTradeModifier> WOVEN =
      MODIFIERS.register("woven", () -> new ArmorSpeedTradeModifier(0xc65c35, -0.005f));
  public static final RegistryObject<ArmorSpeedTradeModifier> PETROUS =
      MODIFIERS.register("petrous", () -> new ArmorSpeedTradeModifier(0x999999, 0.005f));
  public static final RegistryObject<CounterattackModifier> SPLINTERED =
      MODIFIERS.register("splintered", SplinteredModifier::new);

  public static final RegistryObject<DelvingModifier> DELVING =
      MODIFIERS.register("delving", DelvingModifier::new);
  public static final RegistryObject<AerialModifier> AERIAL =
      MODIFIERS.register("aerial", AerialModifier::new);

  public static final RegistryObject<CounterattackModifier> BLIGHTED =
      MODIFIERS.register("blighted", BlightedModifier::new);

  public static final RegistryObject<CounterattackModifier> ENKINDLING =
      MODIFIERS.register("enkindling", EnkindlingModifier::new);

  public static void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    MODIFIERS.register(bus);
  }
}
