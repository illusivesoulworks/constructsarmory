package top.theillusivec4.constructsarmory.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.library.modifiers.Modifier;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.modifier.trait.PaddedModifier;

public class ConstructsArmoryModifiers {

  private static final DeferredRegister<Modifier>
      MODIFIERS = DeferredRegister.create(Modifier.class, ConstructsArmoryMod.MOD_ID);

  public static final RegistryObject<PaddedModifier>
      PADDED = MODIFIERS.register("padded", PaddedModifier::new);

  public static void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    MODIFIERS.register(bus);
  }
}
